package com.possible.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.possible.demo.features.followers.FollowersFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = FollowersFragment()
        fragmentTransaction.add(R.id.activity_main_container, fragment)
        fragmentTransaction.commit()
    }
}