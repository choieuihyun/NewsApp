package com.campusmap.android.wanted_preonboarding_android.news

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.campusmap.android.wanted_preonboarding_android.R
import com.campusmap.android.wanted_preonboarding_android.databinding.CategoriesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.campusmap.android.wanted_preonboarding_android.MainActivity

import androidx.lifecycle.ViewModelProvider
import com.campusmap.android.wanted_preonboarding_android.viewmodel.CategoryitemsViewModel

class Categories : Fragment() {

    private lateinit var binding: CategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("categories", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.categories, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        // 이게 좀 애매한데..
        val activity : Activity? = activity

        if (activity != null) {
            (activity as MainActivity).supportActionBar?.title = "Categories"
        }

        binding.run {
            CoroutineScope(Dispatchers.Main).launch {

                businessButton.setOnClickListener {
                    createFragment(CategoriesItem.newInstance("business"))
                }
                entertainButton.setOnClickListener {
                    createFragment(CategoriesItem.newInstance("entertainment"))
                }
                generalButton.setOnClickListener {
                    createFragment(CategoriesItem.newInstance("general"))
                }
                healthButton.setOnClickListener {
                    createFragment(CategoriesItem.newInstance("health"))
                }
                scienceButton.setOnClickListener {
                    createFragment(CategoriesItem.newInstance("science"))
                }
                sportsButton.setOnClickListener {
                    createFragment(CategoriesItem.newInstance("sports"))
                }
                techButton.setOnClickListener {
                    createFragment(CategoriesItem.newInstance("technology"))
                }
            }
        }

    }

    private fun createFragment(view : Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.topNews_container, view)
            .commit()
    }

    companion object {
        fun newInstance() : Categories {
            return Categories()
        }
    }
}