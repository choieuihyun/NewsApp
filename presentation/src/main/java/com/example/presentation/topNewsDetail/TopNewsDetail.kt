package com.example.presentation.topNewsDetail

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.data.repositoryimpl.TopNewsRepositoryImpl
import com.example.domain.model.ArticleModelParcelize
import com.example.domain.model.SavedModel
import com.example.domain.usecase.AddTopNewsSavedUseCase
import com.example.domain.usecase.DeleteTopNewsSavedUseCase
import com.example.domain.usecase.SharedPreferenceUseCase
import com.example.presentation.R
import com.example.presentation.databinding.TopnewsDetailBinding
import com.example.presentation.presenter.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopNewsDetail : Fragment(), MainContract.TopNewsDetailDeletePresenter,
    MainContract.TopNewsDetailAddPresenter,
    MainContract.TopNewsSharedPreference {

    private lateinit var binding: TopnewsDetailBinding
    private val repository = TopNewsRepositoryImpl.getInstance()

    // https://developer.android.com/training/data-storage/shared-preferences?hl=ko
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {

            sharedPreferences = getSharedPreference()

        }

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
        // 이거 뒤에 파라미터는 키에 값이 없을 시 기본 값임.
        // 뒤의 파라미터에 item.title을 해버려가지구 뭘 누르던 내 코드 구조 상 계속 isChecked = true였음.
        val sharedPreferencesValue = sharedPreferences.getString(item.title, null)
        Log.d("sharedRefTopNews", sharedPreferences.toString())

        binding.topNewsDetailTitle.text = item.title
        binding.topNewsDetailAuthor.text = item.author
        binding.topNewsDetailContent.text = item.content
        binding.topNewsDetailPublish.text = item.publishedAt

        binding.topNewsDetailCheckBox.isChecked = sharedPreferencesValue != null

        Glide.with(view)
            .load(item.urlToImage)
            .error(R.drawable.no_image)
            .into(binding.topNewsDetailImage)

        binding.topNewsDetailImage.setOnClickListener {
            val value1 = sharedPreferences.getString(item.title, null)
            Log.d("sharedData", value1.toString())
        }

        binding.topNewsDetailCheckBox.setOnClickListener {

                if(binding.topNewsDetailCheckBox.isChecked) {

                    val topNewsSaved = SavedModel(
                        title = item.title,
                        author = item.author,
                        content = item.content,
                        publishedAt = item.publishedAt,
                        urlToImage = item.urlToImage,
                        savedButton = true
                    )

                    with(sharedPreferences.edit()) {

                        this?.putString(item.title, item.title)
                        Log.d("sharedpushed", item.title.toString())
                        this?.commit()// apply로 하면 비동기라 좀 느린가?

                    }

                    CoroutineScope(Dispatchers.IO).launch {

                        addTopNews(topNewsSaved)

                    }


                } else {

                    with(sharedPreferences.edit()) {
                        // 제거 시에 saved에 있는 데이터도 없애야함.
                        this?.remove(item.title)
                        Log.d("sharedremoved", sharedPreferences.getString(item.title, item.title).toString())
                        this?.commit()

                    }

                    CoroutineScope(Dispatchers.IO).launch {

                        deleteTopNews(item.title.toString())

                    }

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
        val addTopNews = AddTopNewsSavedUseCase(repository)
        val presenter = TopNewsDetailAddPresenter(addTopNews)

        presenter.addTopNews(saved)
    }

    override suspend fun deleteTopNews(title: String) {
        val deleteTopNews = DeleteTopNewsSavedUseCase(repository)
        val presenter = TopNewsDetailDeletePresenter(deleteTopNews)

        presenter.deleteTopNews(title)
    }

    override suspend fun getSharedPreference(): SharedPreferences {
        val presenter = SharedPreferenceUseCase(repository)

        return presenter()
    }

}