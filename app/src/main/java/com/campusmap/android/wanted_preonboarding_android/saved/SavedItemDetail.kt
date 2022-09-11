package com.campusmap.android.wanted_preonboarding_android.saved

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.databinding.SavedDetailBinding
import com.campusmap.android.wanted_preonboarding_android.roomdb.Saved
import com.campusmap.android.wanted_preonboarding_android.viewmodel.SavedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedItemDetail : Fragment() {

    private lateinit var binding: SavedDetailBinding

    private val sharedPreferences by lazy {
        context?.getSharedPreferences("SavedItemDetails", 0)
    }

    private val savedNewsDetailViewModel: SavedViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SavedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.saved_detail, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = savedNewsDetailViewModel.savedNewsPosition

        CoroutineScope(Dispatchers.IO).launch {

            launch(Dispatchers.Main) {
                savedNewsDetailViewModel.savedNewsLiveData.observe(
                    viewLifecycleOwner,
                    { savedNewsDetail ->
                        try {
                            updateUI(savedNewsDetail[position])
                        } catch (e : IndexOutOfBoundsException) {
                            Log.d("IOE", "IOE")
                        }
                    }
                )
            }.join()


            launch(Dispatchers.IO) {
                val saved = savedNewsDetailViewModel.savedNewsData()
                val savedTitle = saved[position].title
                val savedState = saved[position].savedButton!!
                binding.savedItemDetailCheckbox.isChecked = savedState

                    binding.savedItemDetailCheckbox.setOnClickListener {
                        if (savedState) {
                            savedNewsDetailViewModel.deleteSaved(savedTitle!!)
                            createFragment(SavedItem.newInstance())
                        } else {
                            savedNewsDetailViewModel.addSaved(saved[position])
                        }
                }
            }
        }
    }

    private fun updateUI(item : Saved) {
        binding.savedItemsDetail = item
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
        fun newInstance() : SavedItemDetail {
            return SavedItemDetail()
        }
    }


}