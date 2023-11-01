package com.example.presentation.categoriesDetail

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
import com.example.presentation.databinding.CategoriesItemDetailBinding
import com.example.presentation.presenter.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryNewsDetail : Fragment(), MainContract.TopNewsCategoryDetailAddPresenter,
    MainContract.TopNewsCategoryDetailDeletePresenter,
    MainContract.TopNewsSharedPreference {

    private lateinit var binding: CategoriesItemDetailBinding
    private val repository = TopNewsRepositoryImpl.getInstance()

    private lateinit var sharedPreferences: SharedPreferences

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
        binding = CategoriesItemDetailBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getParcelable<ArticleModelParcelize>("item")!!

        val sharedPreferencesValue = sharedPreferences.getString(item.title, null)

        binding.categoryTitle.text = item.title
        binding.categoryAuthor.text = item.author
        binding.categoryContent.text = item.content
        binding.categoryPublish.text = item.publishedAt

        binding.categoryCheckBox.isChecked = sharedPreferencesValue != null

        Glide.with(view)
            .load(item.urlToImage)
            .error(R.drawable.no_image)
            .into(binding.categoryImageView)

        binding.categoryCheckBox.setOnClickListener {

            if(binding.categoryCheckBox.isChecked) {

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

                    addTopNewsCategory(topNewsSaved)

                }


            } else {

                with(sharedPreferences.edit()) {
                    // 제거 시에 saved에 있는 데이터도 없애야함.
                    this?.remove(item.title)
                    Log.d("sharedremoved", sharedPreferences.getString(item.title, item.title).toString())
                    this?.commit()

                }

                CoroutineScope(Dispatchers.IO).launch {

                    deleteTopNewsCategory(item.title.toString())

                }

            }

        }


    }

    companion object {
        fun newInstance(item: ArticleModelParcelize): CategoryNewsDetail { // 이렇게하는게 아닌거같은데?
            val args = Bundle().apply {
                putParcelable("item", item)
            }
            return CategoryNewsDetail().apply {
                arguments = args
            }
        }
    }

    override suspend fun addTopNewsCategory(saved: SavedModel) {
        val addTopNews = AddTopNewsSavedUseCase(repository)
        val presenter = TopNewsCategoryDetailAddPresenter(addTopNews)

        presenter.addTopNewsCategory(saved)
    }

    override suspend fun deleteTopNewsCategory(title: String) {
        val deleteTopNews = DeleteTopNewsSavedUseCase(repository)
        val presenter = TopNewsCategoryDetailDeletePresenter(deleteTopNews)

        presenter.deleteTopNewsCategory(title)
    }

    override suspend fun getSharedPreference(): SharedPreferences {
        val presenter = SharedPreferenceUseCase(repository)

        return presenter()
    }

}