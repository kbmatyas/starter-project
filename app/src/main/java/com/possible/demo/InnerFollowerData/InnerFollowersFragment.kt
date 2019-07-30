package com.possible.demo.InnerFollowerData

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.possible.demo.GithubLoginDataContainer
import timber.log.Timber

class InnerFollowersFragment : Fragment() {

    private lateinit var innerFollowerDataContainer: GithubLoginDataContainer
    private var innerFollowerUsername: String = ""


    companion object {
        private const val USERNAME = "username"

        fun createFragment(username: String) : Fragment {
            Timber.tag("LEXIE").d("InnerFollowersFragment: createFragment()")
            val b = Bundle()
            b.putString(USERNAME, username)
            var fragment = InnerFollowersFragment()
            fragment.arguments = b
            return fragment
        }
    }
}