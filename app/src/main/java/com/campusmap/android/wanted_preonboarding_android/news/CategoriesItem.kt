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
import com.campusmap.android.wanted_preonboarding_android.ViewModel.CategoryitemsViewModel

import com.campusmap.android.wanted_preonboarding_android.adapter.CategoryItemAdapter
import com.campusmap.android.wanted_preonboarding_android.databinding.CategoriesItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.io.Serializable

// 이거 이름바꾸면 왜 안되냐
class CategoriesItem : Fragment() {

    private lateinit var binding: CategoriesItemBinding // 구조 같아서 그냥 이거 사용. 근데 이게 맞나?
    private lateinit var cateItemRecyclerView: RecyclerView
    private lateinit var itemId: Serializable

    private val categoryItemAdapter by lazy {
        CategoryItemAdapter() // 구조 같아서 요거 사용함.
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
        Log.d("cateitemlist", itemId.toString())

        // viewModel 은 액티비티나 프래그먼트의 context를 참조하지 않게 구현하는것을 지향해야한다.
        createCategoriesItemDetail(itemId.toString())
/*        when (itemId.toString()) {
            "business" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "business")
            }
            "entertainment" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "entertainment")
            }
            "general" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "general")
            }
            "health" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "health")
            }
            "science" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "science")
            }
            "sports" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "sports")
            }
            "technology" -> {
                categoryItemViewModel.getCategoryItemData(requireContext(), "technology")
            }
        }*/

        categoryItemViewModel.getTopNewsResponseLiveData().observe(
            viewLifecycleOwner,
            {
                    categoryItem -> updateUI(categoryItem)
            }
        )

    }

    override fun onStart() {
        super.onStart()
        Log.d("cate", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("cate", "onResume")


        categoryItemAdapter.setOnItemClickListener(object : CategoryItemAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    categoryItemViewModel.loadTopNewsCategoryItem(pos, itemId.toString())
                    createFragment(CategoryItemDetail.newInstance())
                }
            }

        })
    }

    override fun onPause() {
        super.onPause()
        Log.d("cate", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("cate", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("cate", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("cate", "onDestroy")
    }

    private fun updateUI(categoryItem: List<Article?>) {
        categoryItemAdapter.submitList(categoryItem)

    }

    private fun createCategoriesItemDetail(itemId: String) {
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

