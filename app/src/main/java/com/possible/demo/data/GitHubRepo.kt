package com.possible.demo.data

import com.possible.demo.features.followers.FollowerResponse
import com.possible.demo.features.repos.RepoResponse
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GitHubRepo {
    fun getFollowers(login: String): Observable<List<FollowerResponse>> =
            GitHubServiceObject.get.getFollowers(login)

    fun getRepos(login: String): Observable<List<RepoResponse>> =
            GitHubServiceObject.get.getRepos(login)

    suspend fun getFollowersWithCoroutines(login: String): List<FollowerResponse> =
            withContext(Dispatchers.IO) {
                GitHubServiceObject.get.getFollowersWithCoroutines(login)
            }

    suspend fun getReposWithCoroutines(login: String): List<RepoResponse> =
        withContext(Dispatchers.IO) {
            GitHubServiceObject.get.getReposWithCoroutines(login)
        }
}