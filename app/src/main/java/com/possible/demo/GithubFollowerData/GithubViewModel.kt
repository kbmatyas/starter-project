package com.possible.demo.GithubFollowerData

import androidx.lifecycle.ViewModel
import com.possible.demo.GithubClient
import com.possible.demo.GithubLoginDataContainer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class GithubViewModel : ViewModel() {

    var username = ""


    var subject: PublishSubject<Boolean> = PublishSubject.create()

    fun onCompleteStatus(status: Boolean) {
        subject.onNext(status)
    }

    fun searchGithub(username: String, adapter: GithubAdapter) {
        if (username.isNotEmpty()) {
            this.username = username
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