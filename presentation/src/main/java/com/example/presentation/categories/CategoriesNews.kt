package com.example.presentation.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.repositoryimpl.TopNewsRepositoryImpl
import com.example.domain.model.ArticleModel
import com.example.domain.model.ArticleModelParcelize
import com.example.presentation.R
import com.example.presentation.adapter.CategoryNewsAdapter
import com.example.presentation.databinding.CategoriesItemBinding
import com.example.domain.usecase.GetTopNewsCategoryUseCase
import com.example.presentation.categoriesDetail.CategoryNewsDetail
import com.example.presentation.presenter.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.io.Serializable

class CategoriesNews : Fragment(), MainContract.TopNewsView<ArticleModel> {

    private lateinit var binding: CategoriesItemBinding
    private lateinit var categoryNewsRecyclerView: RecyclerView
    private lateinit var itemId: Serializable
    private lateinit var presenter: TopNewsCategoryPresenter

    private val categoryNewsAdapter by lazy {
        CategoryNewsAdapter()
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
        binding = CategoriesItemBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("argument", itemId.toString())

        val repository = TopNewsRepositoryImpl.getInstance()
        //val repository: TopNewsRepository = TopNewsRepositoryImpl(requireContext())
        val categoryNews = GetTopNewsCategoryUseCase(repository)
        presenter = TopNewsCategoryPresenter(this, categoryNews)

        categoryNewsRecyclerView = view.findViewById(R.id.category_recycler_view)
        categoryNewsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        categoryNewsRecyclerView.adapter = categoryNewsAdapter

        CoroutineScope(Dispatchers.Main).launch {

            presenter.getTopNewsCategory(itemId.toString())

            updateNews(categoryNews.getTopNewsCategory())

        }

        categoryNewsAdapter.setOnItemClickListener(object: CategoryNewsAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {

                CoroutineScope(Dispatchers.Main).launch {

                    val item = categoryNews.getTopNewsCategory()

                    // 이렇게 하는 구조가 올바른 구조는 아니라 생각함. 이걸 presenter에서 콜백하는게 올바른 구조일라나?
                    val article = ArticleModelParcelize(
                        title = item?.get(pos)?.title,
                        author = item?.get(pos)?.author,
                        publishedAt = item?.get(pos)?.publishedAt,
                        urlToImage = item?.get(pos)?.urlToImage,
                        content = item?.get(pos)?.content,
                    )

                    createFragment(CategoryNewsDetail.newInstance(article))
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

    override fun updateNews(topNews: List<ArticleModel?>?) {
        categoryNewsAdapter.submitList(topNews)
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
        fun newInstance(itemId: String) : CategoriesNews { // 이렇게하는게 아닌거같은데? ?
            val args = Bundle().apply {
                putSerializable("itemId", itemId)
            }
            return CategoriesNews().apply {
                arguments = args
            }
        }
    }


}
