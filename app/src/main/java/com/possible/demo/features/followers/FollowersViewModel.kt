package com.possible.demo.features.followers

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.possible.demo.data.GitHubRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FollowersViewModel : ViewModel(), LifecycleObserver {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val gitHubRepo = GitHubRepo()

    val followers = MutableLiveData<List<FollowerResponse>>()
    val loadingVisibility = MutableLiveData<Int>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Timber.plant(Timber.DebugTree())

        loadingVisibility.value = View.GONE
    }

    fun onGetFollowers() {
        followers.value = emptyList()
        loadingVisibility.value = View.VISIBLE

        compositeDisposable.add(gitHubRepo.getFollowers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        ::onFollowersLoaded,
                        ::onFollowersLoadedError
                )
        )
    }

    private fun onFollowersLoaded(followers: List<FollowerResponse>) {
        followers.forEach {
            Timber.i("login: " + it.login)
            Timber.i("avatar_url: " + it.avatarUrl)
        }

        this.followers.value = followers

        loadingVisibility.value = View.GONE
    }

    private fun onFollowersLoadedError(throwable: Throwable) {
        Timber.e(throwable.message)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()

        Timber.i("onCleared")
    }
}