package com.example.presentation.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.presentation.R
import com.example.presentation.topNews.TopNewsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if(currentFragment == null) {

            val actionBar = supportActionBar
            actionBar?.title = "NewsApp"

            val fragment = TopNewsFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commitAllowingStateLoss()
        }
    }

}
