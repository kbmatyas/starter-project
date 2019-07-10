package com.possible.demo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubRestApi {

    @GET("users/{username}/followers")
    fun getFollowersList(@Path("username") username: String): Observable<List<Follower>>
}
