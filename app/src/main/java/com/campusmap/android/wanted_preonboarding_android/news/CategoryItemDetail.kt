package com.campusmap.android.wanted_preonboarding_android.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.ViewModel.CategoryitemsViewModel
import com.campusmap.android.wanted_preonboarding_android.databinding.CategoriesItemDetailBinding

class CategoryItemDetail : Fragment() {

    private lateinit var binding: CategoriesItemDetailBinding

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

        val category = categoriesItemDetailViewModel.topNewsCategoryItem
        categoriesItemDetailViewModel.getCategoryItemData(requireContext(), category)

        val pos = categoriesItemDetailViewModel.topNewsCategoryItemPosition
        categoriesItemDetailViewModel.getTopNewsResponseLiveData().observe(
            viewLifecycleOwner,
            {
                categoriesItemDetail -> updateUI(categoriesItemDetail[pos]!!)
        })
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