package com.possible.demo.Network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.possible.demo.Container.GithubLoginDataContainer
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GithubClient {

    private val gitHubService: GitHubService

    init {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_GITHUB)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        gitHubService = retrofit.create(GitHubService::class.java)

    }

    fun getFollowerData(username: String) : Observable<List<GithubLoginDataContainer>> {
        return gitHubService.getFollowers(username)
    }

    companion object {
        const val BASE_URL_GITHUB = "https://api.github.com/"
        const val USERNAME_GITHUB = "talinomedina"
        const val TAG_GITHUB = "GitHub"

        private var githubInstance: GithubClient? = null

        fun getInstance() : GithubClient {
            if (githubInstance == null) {
                githubInstance = GithubClient()
            }
            return githubInstance as GithubClient
        }
    }
}