package com.example.presentation.saved

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.repositoryimpl.TopNewsRepositoryImpl
import com.example.domain.model.SavedModel
import com.example.domain.usecase.GetTopNewsSavedUseCase
import com.example.presentation.R
import com.example.presentation.adapter.SavedNewsAdapter
import com.example.presentation.databinding.SavedBinding
import com.example.presentation.databinding.SavedItemListBinding
import com.example.presentation.presenter.MainActivity
import com.example.presentation.presenter.MainContract
import com.example.presentation.presenter.TopNewsSavedPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedItem : Fragment(), MainContract.TopNewsView<SavedModel> {

    private lateinit var binding: SavedBinding
    private lateinit var savedItemRecyclerView: RecyclerView
    private lateinit var presenter: TopNewsSavedPresenter

    private val savedNewsAdapter by lazy {
        SavedNewsAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as MainActivity).supportActionBar?.title =
            "Saved"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SavedBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = TopNewsRepositoryImpl.getInstance()
        val topNewsSaved = GetTopNewsSavedUseCase(repository)
        presenter = TopNewsSavedPresenter(this, topNewsSaved)

        savedItemRecyclerView = view.findViewById(R.id.saved_recycler_view)
        savedItemRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        savedItemRecyclerView.adapter = savedNewsAdapter

        CoroutineScope(Dispatchers.Main).launch {

            presenter.getTopNewsSaved()

            updateNews(topNewsSaved.getTopNewsSaved())

        }

        savedNewsAdapter.setOnItemClickListener(object : SavedNewsAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    //savedViewModel.setTopNewsItemPosition(pos)
                    //createFragment(SavedItemDetail.newInstance())
                }
            }

        })
    }


    private fun createFragment(view: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.topNews_container, view)
            .commit()
    }

    companion object {
        fun newInstance() : SavedItem {
            return SavedItem()
        }
    }

    override fun updateNews(topNews: List<SavedModel?>?) {
        savedNewsAdapter.submitList(topNews)
    }
}