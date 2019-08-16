package com.possible.demo.data

import com.possible.demo.features.followers.FollowerResponse
import com.possible.demo.features.repos.RepoResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Observable<List<FollowerResponse>>

    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username: String): Observable<List<RepoResponse>>

    @GET("users/{username}/followers")
    suspend fun getFollowersWithCoroutines(@Path("username") username: String): List<FollowerResponse>

    @GET("users/{username}/repos")
    suspend fun getReposWithCoroutines(@Path("username") username: String): List<RepoResponse>
}

object GitHubServiceObject {
    private const val BASE_URL_GITHUB = "https://api.github.com/"

    val get: GitHubService =
            Retrofit.Builder()
                    .baseUrl(BASE_URL_GITHUB)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(GitHubService::class.java)
}