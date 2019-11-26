package com.possible.demo.data.remote.repository

import com.possible.demo.data.DataResult
import com.possible.demo.data.remote.api.GithubUserService
import com.possible.demo.data.remote.cache.FollowerLocalSource
import com.possible.demo.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FollowerRepository @Inject constructor(private val localSource: FollowerLocalSource, private val githubUserService: GithubUserService) {

    suspend fun getFollowersForUser(user: String): DataResult<List<User>, FollowerRequestError> = withContext(Dispatchers.IO) {

        try {
            val result = localSource.getCacheForUser(user) ?: githubUserService.getFollowersOfUser(user).also { localSource.cacheFollowersForUser(user, it) }

            DataResult.Success(result)
        } catch (exception: Throwable) {
            val error = when {
                exception is IOException -> FollowerRequestError.NetworkError
                exception is HttpException && exception.code() == NOT_FOUND_STATUS_CODE -> FollowerRequestError.NotFound
                else -> FollowerRequestError.GeneralError
            }
            DataResult.Error(error)
        }
    }

    sealed class FollowerRequestError : Throwable() {
        object NotFound : FollowerRequestError()
        object NetworkError : FollowerRequestError()
        object GeneralError : FollowerRequestError()
    }

    companion object {
        private const val NOT_FOUND_STATUS_CODE = 404
    }
}