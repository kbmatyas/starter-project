package com.possible.demo.InnerFollowerData

import androidx.fragment.app.Fragment
import com.possible.demo.BaseFragmentActivity
import timber.log.Timber

class InnerFollowersActivity : BaseFragmentActivity() {

    companion object {
        private const val USERNAME = "username"
    }

    override fun createFragment(): Fragment {
        Timber.tag("LEXIE").d("InnerFollowersActivity: createFragment()")
        val username = intent.getSerializableExtra(USERNAME) as String
        return InnerFollowersFragment.createFragment(username)
    }
}