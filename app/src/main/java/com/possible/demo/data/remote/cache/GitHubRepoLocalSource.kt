package com.possible.demo.data.remote.cache

import com.possible.demo.data.model.Repository
import javax.inject.Inject

class GitHubRepoLocalSource @Inject constructor() {

    private val userRepositoriesMap = mutableMapOf<String, Pair<Long, List<Repository>>>()

    fun cacheRepositoryListForUser(user: String, repositories: List<Repository>) {
        userRepositoriesMap[user] = System.currentTimeMillis() to repositories
    }

    fun getCacheForUser(user: String): List<Repository>? {
        val localData = userRepositoriesMap[user]

        return if (localData == null || System.currentTimeMillis() - localData.first > REPO_LIST_VALIDITY) null else localData.second
    }

    companion object {
        private const val REPO_LIST_VALIDITY = 5L * 60 * 1000 // 5 minutes in millis
    }
}