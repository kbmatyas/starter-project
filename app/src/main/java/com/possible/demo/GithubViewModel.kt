package com.possible.demo

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


class GithubViewModel: ViewModel(), LifecycleObserver {

    var data = MutableLiveData<List<GitHubResponse>>()
    var text = "Hello"

}