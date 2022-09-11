package com.campusmap.android.wanted_preonboarding_android.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.viewmodel.CategoryitemsViewModel
import com.campusmap.android.wanted_preonboarding_android.databinding.CategoriesItemDetailBinding
import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved
import com.campusmap.android.wanted_preonboarding_android.viewmodel.TopNewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryItemDetail : Fragment() {

    private lateinit var binding: CategoriesItemDetailBinding
    private var id: Int? = null
    private var getTitleKey: String? = null
    private var getCheckedKey: String? = null

    private val sharedPreferences by lazy {
        context?.getSharedPreferences("CategoryItemDetail", 0)
    }

    private val categoriesItemDetailViewModel: CategoryitemsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CategoryitemsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.categories_item_detail, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = categoriesItemDetailViewModel.topNewsCategoryItemPosition

        val category = categoriesItemDetailViewModel.topNewsCategoryItem

        CoroutineScope(Dispatchers.IO).launch {

            categoriesItemDetailViewModel.getCategoryItemData(requireContext(), category)

            launch(Dispatchers.Main) {
                categoriesItemDetailViewModel.getTopNewsCategoryResponseLiveData().observe(
                    viewLifecycleOwner,
                    { categoriesItemDetail ->
                        updateUI(categoriesItemDetail[position]!!)
                        getTitleKey = categoriesItemDetail[position]?.title
                        getCheckedKey = categoriesItemDetail[position]?.title
                    })
                }.join()

            launch(Dispatchers.Main) {

                binding.categoriesItemDetailCheckbox.isChecked =
                    sharedPreferences?.getString(getTitleKey, "") != ""

                }.join()

            launch(Dispatchers.IO) {

                val article = categoriesItemDetailViewModel.getTopNewsCategoryResponseData()

                binding.categoriesItemDetailCheckbox.setOnCheckedChangeListener { _, isChecked ->
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
                        categoriesItemDetailViewModel.addSaved(saved)

                        sharedPreferences?.edit()?.putBoolean("${saved.title}", isChecked)?.apply()
                        sharedPreferences?.edit()?.putString("${saved.title}", saved.title)?.apply()
                    } else {

                        sharedPreferences?.edit()?.putBoolean(getCheckedKey, isChecked)?.apply()
                        sharedPreferences?.edit()?.putString(getTitleKey, "")?.apply()
                        categoriesItemDetailViewModel.deleteSaved(getTitleKey!!)
                    }
                }
            }
        }
    }

    private fun updateUI(item: Article) {
        binding.categoryItemsDetail = item
    }

    companion object {
        fun newInstance() : CategoryItemDetail {
            return CategoryItemDetail()
        }
    }


}