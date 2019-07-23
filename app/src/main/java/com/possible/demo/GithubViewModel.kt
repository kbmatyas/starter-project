package com.possible.demo

import android.arch.lifecycle.ViewModel
import com.possible.demo.Container.GithubLoginDataContainer
import com.possible.demo.Network.GithubClient
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class GithubViewModel : ViewModel() {


    var subject: PublishSubject<Boolean> = PublishSubject.create()

    fun onCompleteStatus(status: Boolean) {
        subject.onNext(status)
    }

    fun searchGithub(username: String, adapter: GithubAdapter) {
        if (username.isNotEmpty()) {
            GithubClient.getInstance()
                    .getFollowerData(username)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<List<GithubLoginDataContainer>> {

                        override fun onComplete() {
                            onCompleteStatus(true)
                        }

                        override fun onError(e: Throwable) {
                            onCompleteStatus(true)
                        }

                        override fun onNext(t: List<GithubLoginDataContainer>) {
                            onCompleteStatus(false)
                            adapter.setGithubFollowerList(t)
                        }

                        override fun onSubscribe(d: Disposable) {}
                    })
        }
    }
}