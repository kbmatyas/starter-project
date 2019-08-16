package com.possible.demo.features.followers

import timber.log.Timber

data class Follower(
        val login: String,
        val avatarUrl: String,
        val toReposFragmentCb: (login: String) -> Unit
) {
    fun onClick() {
        Timber.i("onClick: $login, $avatarUrl")

        toReposFragmentCb(login)
    }
}