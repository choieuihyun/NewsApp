package com.example.presentation.topnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.data.db.local.entity.Saved
import com.example.data.repositoryimpl.TopNewsRepositoryImpl
import com.example.domain.model.ArticleModelParcelize
import com.example.domain.model.SavedModel
import com.example.domain.usecase.AddTopNewsSavedUseCase
import com.example.domain.usecase.GetTopNewsSavedUseCase
import com.example.presentation.R
import com.example.presentation.databinding.TopnewsDetailBinding
import com.example.presentation.presenter.MainContract
import com.example.presentation.presenter.TopNewsDetailPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsDetail : Fragment(), MainContract.TopNewsPresenter.TopNewsDetailPresenter {

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

        binding = TopnewsDetailBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이거 lateinit 사용해서 onCreate에서 초기화 하는 구조로 하려다가 한 두시간 헤멤.
        val item = arguments?.getParcelable<ArticleModelParcelize>("item")!!

        binding.topNewsDetailTitle.text = item.title
        binding.topNewsDetailAuthor.text = item.author
        binding.topNewsDetailContent.text = item.content
        binding.topNewsDetailPublish.text = item.publishedAt
        Glide.with(view)
            .load(item.urlToImage)
            .error(R.drawable.no_image)
            .into(binding.topNewsDetailImage)

        Log.d("topNewsDetailAr", item.toString())

        binding.topNewsDetailCheckBox.setOnClickListener {

            val topNewsSaved = SavedModel(
                title = item.title,
                author = item.author,
                content = item.content,
                publishedAt = item.publishedAt,
                urlToImage = item.urlToImage,
                savedButton = true
            )

            CoroutineScope(Dispatchers.IO).launch {

                addTopNews(topNewsSaved)

            }

        }


    }

    companion object {

        fun newInstance(item: ArticleModelParcelize) : TopNewsDetail {
            val args = Bundle().apply {
                putParcelable("item", item)
            }
            return TopNewsDetail().apply {
                arguments = args
            }
        }
    }

    override suspend fun addTopNews(saved: SavedModel) {
        val repository = TopNewsRepositoryImpl.getInstance()
        val topNews = AddTopNewsSavedUseCase(repository)
        val presenter = TopNewsDetailPresenter(topNews)

        presenter.addTopNews(saved)
    }

}