package com.example.presentation.savedDetail

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.data.repositoryimpl.TopNewsRepositoryImpl
import com.example.domain.model.SavedModel
import com.example.domain.model.SavedModelParcelize
import com.example.domain.usecase.AddTopNewsSavedUseCase
import com.example.domain.usecase.DeleteTopNewsSavedUseCase
import com.example.domain.usecase.SharedPreferenceUseCase
import com.example.presentation.R
import com.example.presentation.databinding.SavedDetailBinding
import com.example.presentation.presenter.MainContract
import com.example.presentation.presenter.SharedPreferencePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedItemDetail : Fragment(),
MainContract.TopNewsSavedDetailAddPresenter,
MainContract.TopNewsSavedDetailDeletePresenter,
MainContract.TopNewsSharedPreference {

    private lateinit var binding: SavedDetailBinding
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
        binding = SavedDetailBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getParcelable<SavedModelParcelize>("item")!!

        binding.title.text = item.title
        binding.author.text = item.author
        binding.content.text = item.content
        binding.publishedAt.text = item.publishedAt
        binding.savedItemDetailCheckbox.isChecked = item.savedButton!!
        Glide.with(view)
            .load(item.urlToImage)
            .error(R.drawable.no_image)
            .into(binding.imageView)

        Log.d("sharedRefSaved", sharedPreferences.toString())

        var checkBox = binding.savedItemDetailCheckbox.isChecked

        val saved = SavedModel( // 이 인스턴스는 화면 벗어나기 전까진 유지되므로
                                // savedDetail에서 즐겨찾기 해제 했다가 다시 추가할 땐 이 인스턴스를 사용함.
            id = item.id,
            title = item.title,
            author = item.author,
            publishedAt = item.publishedAt,
            urlToImage = item.urlToImage,
            content = item.content,
            savedButton = item.savedButton
        )

        binding.savedItemDetailCheckbox.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                if (checkBox) {

                    with(sharedPreferences.edit()) {
                        this?.remove(item.title)
                        this?.commit()
                    }

                    checkBox = false
                    deleteTopNewsSaved(item.title.toString())

                } else {
                    with(sharedPreferences.edit()) {
                        this?.putString(item.title, item.title)
                        this?.commit()// apply로 하면 비동기라 좀 느린가?
                    }

                    checkBox = true
                    addTopNewsSaved(saved)
                }
            }
        }
    }

    companion object {
        fun newInstance(item: SavedModelParcelize): SavedItemDetail {
            val args = Bundle().apply {
                putParcelable("item", item)
            }
            return SavedItemDetail().apply {
                arguments = args
            }
        }
    }

    override suspend fun addTopNewsSaved(saved: SavedModel) {
        val addUseCase = AddTopNewsSavedUseCase(repository)
        val presenter = TopNewsSavedDetailAddPresenter(addUseCase)
        presenter.addTopNewsSaved(saved)
    }

    override suspend fun deleteTopNewsSaved(title: String) {
        val deleteTopNews = DeleteTopNewsSavedUseCase(repository)
        val presenter = TopNewsSavedDetailDeletePresenter(deleteTopNews)
        presenter.deleteTopNewsSaved(title)
    }

    override suspend fun getSharedPreference(): SharedPreferences {

        val sharedPreferencesUseCase = SharedPreferenceUseCase(repository)
        val presenter = SharedPreferencePresenter(sharedPreferencesUseCase)

        return presenter.getSharedPreference()
    }

}