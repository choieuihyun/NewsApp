package com.campusmap.android.wanted_preonboarding_android.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.viewmodel.TopNewsViewModel
import com.campusmap.android.wanted_preonboarding_android.databinding.TopnewsDetailBinding
import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private var savedCount = 0

class TopNewsDetail : Fragment() {

    private lateinit var binding: TopnewsDetailBinding
    private var id: Int? = null
    private var getTitleKey: String? = null
    private var getCheckedKey: String? = null
    private var title: String? = null
    private var checked: Boolean? = false

    // https://developer.android.com/training/data-storage/shared-preferences?hl=ko
    private val sharedPreferences by lazy {
        context?.getSharedPreferences("TopNewsDetails", 0)
    }

    private val topNewsDetailViewModel: TopNewsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TopNewsViewModel::class.java)
    }

/*    private val topNewsDetailViewModel: TopNewsViewModel by lazy {
        ViewModelProvider(this).get(TopNewsViewModel::class.java)
    }*/

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

        val pos = topNewsDetailViewModel.topNewsPosition

        CoroutineScope(Dispatchers.IO).launch {

            Log.d("TopNewsDetailPos", pos.toString())

            launch(Dispatchers.Main) {
                topNewsDetailViewModel.getTopNewsAllResponseLiveData().observe(
                    viewLifecycleOwner,
                    { topNewsDetail ->
                        updateUI(topNewsDetail[pos]!!)
                        getTitleKey = topNewsDetail[pos]?.title
                        getCheckedKey = topNewsDetail[pos]?.title
                        Log.d("getTitleKey", getTitleKey.toString())
                        Log.d("getCheckedKey", getCheckedKey.toString())

                    }
                )
            }.join()

            Log.d("getTitleKey2", getTitleKey.toString())
            Log.d("getCheckedKey2", getCheckedKey.toString())

            launch(Dispatchers.Main) {

                binding.topNewsDetailCheckBox.isChecked =
                    sharedPreferences?.getString(getTitleKey, "") != ""


            }

            launch(Dispatchers.IO) {

                val article = topNewsDetailViewModel.getTopNewsAllResponseData()
                lateinit var savedd: Saved

                binding.topNewsDetailCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) { // 이게맞아. 눌렀을 때
                        val saved = Saved(
                            id,
                            article?.get(pos)?.author,
                            article?.get(pos)?.content,
                            article?.get(pos)?.publishedAt,
                            article?.get(pos)?.title,
                            article?.get(pos)?.urlToImage,
                            true
                        )
                        topNewsDetailViewModel.addSaved(saved)

                        savedd = saved
                        Log.d("savedTitle", saved.title.toString())

                        // 아 title로하면 title 같은거 다 지워져서 안되고
                        // saved의 id를 받아와서 해야하는데.. 사실 중복으로 등록은 안되서 위험도가 조금 낮긴한데
                        // 이게 title로 지우는게 성립되려면 같은 제목의 기사가 안나와야함

                        sharedPreferences?.edit()?.putBoolean("${saved.title}", true)?.apply()
                        sharedPreferences?.edit()?.putString("${saved.title}", saved.title)?.apply()

                        Log.d(
                            "currentSharedPref1",
                            sharedPreferences?.getString(saved.title, "").toString()
                        )

                    } else {
                        //val savedTitle = topNewsDetailViewModel.getTitle(title!!) 요거 할 필요가 없었나
                        //Log.d("savedTitle2", savedTitle.toString())
                        Log.d("getTitleKey3", sharedPreferences?.getString(getTitleKey, "").toString())

                        sharedPreferences?.edit()?.putBoolean(getCheckedKey, false)?.apply()
                        sharedPreferences?.edit()?.putString(getTitleKey, "")?.apply()

                        //Log.d("deleteTitle", savedTitle)
                        topNewsDetailViewModel.deleteSaved(getTitleKey!!)
                        Log.d("getTitleKey4", sharedPreferences?.getString(getTitleKey, "").toString())
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
        binding.topNewsDetailCheckBox.isChecked = checked!!


    }

    private fun updateUI(item : Article) {
        binding.topNewsDetail = item
    }

    private fun getKey(key: String): String {

        return key
    }

    companion object {
        fun newInstance() : TopNewsDetail {
            return TopNewsDetail()
        }
    }

}