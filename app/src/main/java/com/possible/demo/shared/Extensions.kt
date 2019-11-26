package com.possible.demo.shared

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T> LiveData<T>.debounce(duration: Long): LiveData<T> {

    val handler = Handler(Looper.getMainLooper())

    val debouncedLiveData = MediatorLiveData<T>()

    val runnable = Runnable { debouncedLiveData.value = this.value }

    debouncedLiveData.addSource(this) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, duration)
    }

    return debouncedLiveData
}