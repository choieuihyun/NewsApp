package com.example.presentation.categories

import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.presentation.R
import com.example.presentation.databinding.CategoriesBinding
import com.example.presentation.presenter.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        val geo : Geocoder = Geocoder(requireContext())

        val address : MutableList<Address>? = geo.getFromLocation(35.814243358044095, 127.09176491959228, 1)
        Log.d("sdfsdfsdf", address?.get(0).toString())
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
                    createFragment(CategoriesNews.newInstance("business"))
                }
                entertainButton.setOnClickListener {
                    createFragment(CategoriesNews.newInstance("entertainment"))
                }
                generalButton.setOnClickListener {
                    createFragment(CategoriesNews.newInstance("general"))
                }
                healthButton.setOnClickListener {
                    createFragment(CategoriesNews.newInstance("health"))
                }
                scienceButton.setOnClickListener {
                    createFragment(CategoriesNews.newInstance("science"))
                }
                sportsButton.setOnClickListener {
                    createFragment(CategoriesNews.newInstance("sports"))
                }
                techButton.setOnClickListener {
                    createFragment(CategoriesNews.newInstance("technology"))
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