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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class FollowersViewModel : ViewModel(), LifecycleObserver, CoroutineScope {

    private var followersViewModelJob: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + followersViewModelJob

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val gitHubRepo = GitHubRepo()

    val login = MutableLiveData<String>().apply {
        value = "jakelangfeldt"
    }
    val followers = MutableLiveData<List<Follower>>()
    val loadingVisibility = MutableLiveData<Int>()
    val errorVisibility = MutableLiveData<Int>()

    lateinit var toReposFragmentCb: (login: String) -> Unit

    private val usingCoroutines = true

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Timber.plant(Timber.DebugTree())

        loadingVisibility.value = View.GONE
        errorVisibility.value = View.GONE
    }

    fun onGetFollowers() {
        if (usingCoroutines) {
            getFollowersWithCoroutines()
        } else {
            getFollowers()
        }
    }

    private fun getFollowers() {
        followers.value = mutableListOf()

        loadingVisibility.value = View.VISIBLE
        errorVisibility.value = View.GONE

        login.value?.let {
            compositeDisposable.add(gitHubRepo.getFollowers(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            ::onFollowersLoaded,
                            ::onFollowersLoadedError
                    )
            )
        }
    }

    private fun getFollowersWithCoroutines() {
        followers.value = mutableListOf()

        loadingVisibility.value = View.VISIBLE
        errorVisibility.value = View.GONE

        launch(Dispatchers.Main) {
            runCatching {
                login.value?.let {
                    gitHubRepo.getFollowersWithCoroutines(it)
                }
            }.onSuccess { followerResponseList ->
                followerResponseList?.let {
                    onFollowersLoaded(it)
                }
            }.onFailure { throwable ->
                onFollowersLoadedError(throwable)
            }
        }
    }

    private fun onFollowersLoaded(followerResponseList: List<FollowerResponse>) {
        followers.value = followerResponseList.mapNotNull {
            Timber.i("login: " + it.login)
            Timber.i("avatar_url: " + it.avatarUrl)

            if (it.login != null && it.avatarUrl != null) {
                Follower(it.login, it.avatarUrl, toReposFragmentCb)
            } else {
                null
            }
        }

        loadingVisibility.value = View.GONE
    }

    private fun onFollowersLoadedError(throwable: Throwable) {
        Timber.e(throwable.message)

        loadingVisibility.value = View.GONE
        errorVisibility.value = View.VISIBLE
    }

    override fun onCleared() {
        super.onCleared()

        followersViewModelJob.cancel()
        compositeDisposable.clear()

        Timber.i("onCleared")
    }
}