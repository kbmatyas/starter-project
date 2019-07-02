package com.possible.demo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Observable<List<GitHubResponse>>
}