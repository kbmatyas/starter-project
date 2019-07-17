package com.possible.demo

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableList

class GithubViewModel : ViewModel() {

    var items = listOf("One","Two","Three","Four","Five")

}