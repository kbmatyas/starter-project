package com.possible.demo

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var disposable: CompositeDisposable = CompositeDisposable()
    var adapter = GithubAdapter()

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)

        val model = ViewModelProviders.of(this).get(GithubViewModel::class.java)

        setupViews()
//        val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL_GITHUB)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build()
//        val gitHubService = retrofit.create(GitHubService::class.java)
//
//        disposable.add(gitHubService.getFollowers(USERNAME_GITHUB)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(
//                        this@MainActivity::onSuccess,
//                        this@MainActivity::onError
//                ))
    }

    private fun setupViews() {
        recyclerView = findViewById(R.id.githubRecyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    }



    override fun onDestroy() {
        super.onDestroy()
        disposable?.clear()
    }

    fun onSuccess(followers: List<GitHubResponse>) {
        followers.forEach {
            Log.i(TAG_GITHUB, it.login)
        }
    }

    fun onError(throwable: Throwable) {
        Log.i(TAG_GITHUB, throwable.message)
    }

    companion object {
        const val BASE_URL_GITHUB = "https://api.github.com/"
        const val USERNAME_GITHUB = "talinomedina"
        const val TAG_GITHUB = "GitHub"
    }
}
