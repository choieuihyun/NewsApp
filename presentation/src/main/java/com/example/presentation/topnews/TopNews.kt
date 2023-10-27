package com.example.presentation.topnews

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.repositoryimpl.TopNewsRepositoryImpl
import com.example.presentation.R
import com.example.presentation.adapter.TopNewsAdapter
import com.example.presentation.databinding.TopnewsBinding
import com.example.presentation.presenter.MainActivity
import com.example.presentation.presenter.MainContract
import com.example.presentation.presenter.TopNewsPresenter
import com.example.domain.model.ArticleModel
import com.example.domain.repository.TopNewsRepository
import com.example.domain.usecase.GetTopNewsUseCase
import kotlinx.coroutines.*


class TopNews() : Fragment(), MainContract.TopNewsView {

    private lateinit var binding: TopnewsBinding
    private lateinit var topNewsRecyclerView: RecyclerView
    private lateinit var presenter: TopNewsPresenter

    private val topNewsAdapter by lazy {
        TopNewsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TopnewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 올바른 구조가 아닌 것 같음. 프레임워크 사용 안하고 해보려다가 이렇게 된건데 이거 아니면 다 거기서 거기네
        // 이렇게 하면 presentation layer에서 data layer의 TopNewsRepositoryImpl 인스턴스를 생성해버림.
        val repository: TopNewsRepository = TopNewsRepositoryImpl(requireContext())
        val topNews = GetTopNewsUseCase(repository)
        presenter = TopNewsPresenter(this, topNews)

        topNewsRecyclerView = view.findViewById(R.id.topNews_recycler_view)
        topNewsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        topNewsRecyclerView.adapter = topNewsAdapter

        CoroutineScope(Dispatchers.IO).launch {

            presenter.getTopNews(requireContext())

            updateNews(topNews.getTopNews())

            Log.d("TopNews", topNews.getTopNews().toString())

        }


    }

    override fun onResume() {
        super.onResume()

        val activity : Activity? = activity

        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "TopNews"
        }

        topNewsAdapter.setOnItemClickListener(object : TopNewsAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    createFragment(TopNewsDetail.newInstance())
                }
            }

        })

    }

    companion object {
        fun newInstance() : TopNews {
            return TopNews()
        }
    }


    override fun updateNews(topNews: List<ArticleModel?>?) {
        topNewsAdapter.submitList(topNews)
    }

    private fun createFragment(view: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.topNews_container, view)
            .commit()
    }



}