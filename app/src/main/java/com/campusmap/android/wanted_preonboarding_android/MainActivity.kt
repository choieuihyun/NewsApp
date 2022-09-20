package com.campusmap.android.wanted_preonboarding_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.campusmap.android.wanted_preonboarding_android.news.TopNews

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 여기 practice2 추가요

        // practice 변경사항

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if(currentFragment == null) {

            val actionBar = supportActionBar
            actionBar?.title = "NewsApp"

            val fragment = TopNewsFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commitAllowingStateLoss()
        }
    }

}
