package com.campusmap.android.wanted_preonboarding_android.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.viewmodel.CategoryitemsViewModel

import com.campusmap.android.wanted_preonboarding_android.adapter.CategoryItemAdapter
import com.campusmap.android.wanted_preonboarding_android.databinding.CategoriesItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.io.Serializable

class CategoriesItem : Fragment() {

    private lateinit var binding: CategoriesItemBinding
    private lateinit var cateItemRecyclerView: RecyclerView
    private lateinit var itemId: Serializable

    private val categoryItemAdapter by lazy {
        CategoryItemAdapter()
    }

    private val categoryItemViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CategoryitemsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemId = arguments?.getSerializable("itemId")!!

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.categories_item, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cateItemRecyclerView = view.findViewById(R.id.cateItem_recycler_view)
        cateItemRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cateItemRecyclerView.adapter = categoryItemAdapter

        createCategoriesItem(itemId.toString())

        categoryItemViewModel.getTopNewsCategoryResponseLiveData().observe(
            viewLifecycleOwner,
            {
                    categoryItem -> updateUI(categoryItem)
            }
        )

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()


        categoryItemAdapter.setOnItemClickListener(object : CategoryItemAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    categoryItemViewModel.setTopNewsCategoryItemInfo(pos, itemId.toString())
                    createFragment(CategoryItemDetail.newInstance())
                }
            }

        })
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun updateUI(categoryItem: List<Article?>) {
        categoryItemAdapter.submitList(categoryItem)

    }

    private fun createCategoriesItem(itemId: String) {
        categoryItemViewModel.getCategoryItemData(requireContext(), itemId)
    }

    private fun createFragment(view: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.topNews_container, view)
            .commit()
    }

    companion object {
        fun newInstance(itemId: String) : CategoriesItem { // 이렇게하는게 아닌거같은데? ?
            val args = Bundle().apply {
                putSerializable("itemId", itemId)
            }
            return CategoriesItem().apply {
                arguments = args
            }
        }
    }
}

