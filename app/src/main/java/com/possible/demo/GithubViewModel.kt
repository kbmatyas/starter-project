package com.possible.demo

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GithubViewModel: ViewModel(), LifecycleObserver {

    var data = MutableLiveData<List<GitHubResponse>>()

}