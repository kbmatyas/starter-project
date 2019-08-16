package com.possible.demo.data

import com.possible.demo.features.followers.FollowerResponse
import com.possible.demo.features.repos.RepoResponse
import io.reactivex.Observable

class GitHubRepo {
    fun getFollowers(login: String): Observable<List<FollowerResponse>> =
            GitHubServiceObject.get.getFollowers(login)

    fun getRepos(login: String): Observable<List<RepoResponse>> =
            GitHubServiceObject.get.getRepos(login)
}