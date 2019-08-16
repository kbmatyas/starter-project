package com.possible.demo.features.repos

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

class ReposViewModel : ViewModel(), LifecycleObserver {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val gitHubRepo = GitHubRepo()

    lateinit var login: String

    val repos = MutableLiveData<List<Repo>>()
    val loadingVisibility = MutableLiveData<Int>()
    val errorVisibility = MutableLiveData<Int>()

    lateinit var toReposFragmentCb: (login: String) -> Unit

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        loadingVisibility.value = View.GONE
        errorVisibility.value = View.GONE

        getFollowers()
    }

    private fun getFollowers() {
        repos.value = mutableListOf()

        loadingVisibility.value = View.VISIBLE
        errorVisibility.value = View.GONE

        login.let {
            compositeDisposable.add(gitHubRepo.getRepos(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            ::onReposLoaded,
                            ::onReposLoadedError
                    )
            )
        }
    }

    private fun onReposLoaded(repoResponseList: List<RepoResponse>) {
        repos.value = repoResponseList.mapNotNull {
            Timber.i("name: " + it.name)

            Repo(it.name)
        }

        loadingVisibility.value = View.GONE
    }

    private fun onReposLoadedError(throwable: Throwable) {
        Timber.e(throwable.message)

        loadingVisibility.value = View.GONE
        errorVisibility.value = View.VISIBLE
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()

        Timber.i("onCleared")
    }
}