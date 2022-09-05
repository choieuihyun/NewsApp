package com.campusmap.android.wanted_preonboarding_android.news

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.campusmap.android.wanted_preonboarding_android.MainActivity
import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.RetrofitClient
import com.campusmap.android.wanted_preonboarding_android.databinding.CategoriesItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class CategoriesItem : Fragment() {

    private lateinit var binding: CategoriesItemBinding
    private lateinit var cateItemRecyclerView: RecyclerView
    private lateinit var itemId: Serializable

    private val cateItemAdapter by lazy {
        TopNewsAdapter()
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
        binding = DataBindingUtil.inflate(inflater,R.layout.categories_item, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cateItemRecyclerView = view.findViewById(R.id.cateItem_recycler_view)
        cateItemRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cateItemRecyclerView.adapter = cateItemAdapter
        Log.d("cateitemlist", itemId.toString())


        when(itemId.toString()) {
            "business" -> getCategoryData("business")
            "entertainment" -> getCategoryData("entertainment")
            "general" -> getCategoryData("general")
            "health" -> getCategoryData("health")
            "science" -> getCategoryData("science")
            "sports" -> getCategoryData("sports")
            "technology" -> getCategoryData("technology")
        }

    }
   private fun getCategoryData(category: String) {

       val activity : Activity? = activity

       if (activity != null) {
           (activity as MainActivity).supportActionBar?.title = "Category - ${category.replaceFirstChar { it.uppercase() }}"
       }

        val service = RetrofitClient.topNewsService

        service.getTopNewsCategoryData(getString(R.string.api_key), category).enqueue(object :
            Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d("TAG", response.body().toString())
                    val result = response.body()?.articles
                    cateItemAdapter.submitList(result)
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("TAG", t.message.toString())
            }

        })
    }


    companion object {
        fun newInstance(itemId: String) : CategoriesItem {
            val args = Bundle().apply {
                putSerializable("itemId", itemId)
            }
            return CategoriesItem().apply {
                arguments = args
            }
        }
    }
}