package com.possible.demo.data

import com.possible.demo.features.followers.FollowerResponse
import io.reactivex.Observable

class GitHubRepo() {
    fun getFollowers(): Observable<List<FollowerResponse>> =
            GitHubServiceObject.get.getFollowers(USERNAME_GITHUB)

    companion object {
        const val USERNAME_GITHUB = "jakelangfeldt"
    }
}