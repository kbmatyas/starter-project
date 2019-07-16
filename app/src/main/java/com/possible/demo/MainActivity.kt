package com.possible.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_GITHUB)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val gitHubService = retrofit.create(GitHubService::class.java)

        disposable.add(gitHubService.getFollowers(USERNAME_GITHUB)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        this@MainActivity::onSuccess,
                        this@MainActivity::onError
                ))
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.clear()
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
