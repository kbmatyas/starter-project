package com.possible.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.possible.demo.features.followers.FollowersFragment
import com.possible.demo.features.repos.ReposFragment
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private val toReposFragmentCb: (login: String) -> Unit = { login ->
        val reposFragment = ReposFragment(login)
        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_container, reposFragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())

        startFollowersFragment()
    }

    private fun startFollowersFragment() {
        val followersFragment = FollowersFragment()
        followersFragment.toReposFragmentCb = toReposFragmentCb
        supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_container, followersFragment)
                .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}