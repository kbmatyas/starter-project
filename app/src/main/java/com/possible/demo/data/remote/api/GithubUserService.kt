package com.possible.demo.data.remote.api

import com.possible.demo.data.model.Repository
import com.possible.demo.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubUserService {

    @GET("users/{username}/followers")
    suspend fun getFollowersOfUser(@Path("username") userName: String): List<User>

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(@Path("username") userName: String): List<Repository>
}