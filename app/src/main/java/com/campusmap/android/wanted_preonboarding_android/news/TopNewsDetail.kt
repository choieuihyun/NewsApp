package com.campusmap.android.wanted_preonboarding_android.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.TopNewsFragment
import com.campusmap.android.wanted_preonboarding_android.viewmodel.TopNewsViewModel
import com.campusmap.android.wanted_preonboarding_android.databinding.TopnewsDetailBinding
import com.campusmap.android.wanted_preonboarding_android.retrofit.Article
import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsDetail : Fragment() {

    private lateinit var binding: TopnewsDetailBinding
    private var id: Int? = null
    private var getTitleKey: String? = null
    private var getCheckedKey: String? = null
    private var checked: Boolean? = false

    // https://developer.android.com/training/data-storage/shared-preferences?hl=ko
    private val sharedPreferences by lazy {
        context?.getSharedPreferences("TopNewsDetails", 0)
    }

    private val topNewsDetailViewModel: TopNewsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TopNewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.topnews_detail, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = topNewsDetailViewModel.topNewsPosition

        CoroutineScope(Dispatchers.IO).launch {


            launch(Dispatchers.Main) {
                topNewsDetailViewModel.getTopNewsAllResponseLiveData().observe(
                    viewLifecycleOwner,
                    { topNewsDetail ->
                        updateUI(topNewsDetail[position]!!)
                        getTitleKey = topNewsDetail[position]?.title
                        getCheckedKey = topNewsDetail[position]?.title
                    }
                )
            }.join()



            launch(Dispatchers.Main) {

                binding.topNewsDetailCheckBox.isChecked =
                    sharedPreferences?.getString(getTitleKey, "") != ""

            }.join()

            launch(Dispatchers.IO) {

                val article = topNewsDetailViewModel.getTopNewsAllResponseData()

                binding.topNewsDetailCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        val saved = Saved(
                            id,
                            article?.get(position)?.author,
                            article?.get(position)?.content,
                            article?.get(position)?.publishedAt,
                            article?.get(position)?.title,
                            article?.get(position)?.urlToImage,
                            true
                        )
                        topNewsDetailViewModel.addSaved(saved)

                        sharedPreferences?.edit()?.putBoolean("${saved.title}", true)?.apply()
                        sharedPreferences?.edit()?.putString("${saved.title}", saved.title)?.apply()

                    } else {

                        sharedPreferences?.edit()?.putBoolean(getCheckedKey, false)?.apply()
                        sharedPreferences?.edit()?.putString(getTitleKey, "")?.apply()

                        topNewsDetailViewModel.deleteSaved(getTitleKey!!)
                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()


    }

    override fun onDestroy() {
        super.onDestroy()


    }

    private fun updateUI(item : Article) {
        binding.topNewsDetail = item
    }

    companion object {

        fun newInstance() : TopNewsDetail {
            return TopNewsDetail()
        }
    }

}