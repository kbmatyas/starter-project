package com.possible.demo.data.remote.repository

import com.possible.demo.data.DataResult
import com.possible.demo.data.ServerError
import com.possible.demo.data.remote.api.GithubUserService
import com.possible.demo.data.model.Repository
import com.possible.demo.data.remote.cache.GitHubRepoLocalSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class GitHubRepoRepository @Inject constructor(private val localSource: GitHubRepoLocalSource, private val githubUserService: GithubUserService) {

    suspend fun getUserRepositories(user: String): DataResult<List<Repository>, ServerError> = withContext(Dispatchers.IO) {
        try {
            val result = localSource.getCacheForUser(user) ?: githubUserService.getUserRepositories(user).also {
                localSource.cacheRepositoryListForUser(user, it)
            }
            DataResult.Success(result)
        } catch (exception: Throwable) {
            when (exception) {
                is IOException -> DataResult.Error(ServerError.NetworkError)
                else -> DataResult.Error(ServerError.GeneralError)
            }
        }
    }
}