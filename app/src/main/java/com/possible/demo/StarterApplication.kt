package com.possible.demo

import com.possible.demo.injection.DaggerStarterAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class StarterApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = DaggerStarterAppComponent.builder().build()
}