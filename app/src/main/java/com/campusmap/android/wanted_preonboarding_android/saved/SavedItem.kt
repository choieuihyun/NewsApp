package com.campusmap.android.wanted_preonboarding_android.saved

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.campusmap.android.wanted_preonboarding_android.MainActivity
import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.adapter.SavedItemAdapter
import com.campusmap.android.wanted_preonboarding_android.databinding.SavedBinding
import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved
import com.campusmap.android.wanted_preonboarding_android.viewmodel.SavedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedItem : Fragment() {

    private lateinit var binding: SavedBinding
    private lateinit var savedItemRecyclerView: RecyclerView

    private val savedItemAdapter by lazy {
        SavedItemAdapter()
    }

    private val savedViewModel: SavedViewModel by lazy { // TopNews랑 연동되니까
        ViewModelProvider(requireActivity()).get(SavedViewModel::class.java)
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

        binding = DataBindingUtil.inflate(inflater, R.layout.saved, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedItemRecyclerView = view.findViewById(R.id.saved_recycler_view)
        savedItemRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        savedItemRecyclerView.adapter = savedItemAdapter

        savedViewModel.savedNewsLiveData.observe(
            viewLifecycleOwner,
            {
                savedData -> updateUI(savedData)
            }
        )

    }

    override fun onResume() {
        super.onResume()
        savedItemAdapter.setOnItemClickListener(object : SavedItemAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    savedViewModel.setTopNewsItemPosition(pos)
                    createFragment(SavedItemDetail.newInstance())
                }
            }

        })
    }

    private fun updateUI(item : List<Saved>) {
        savedItemAdapter.submitList(item)
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
}