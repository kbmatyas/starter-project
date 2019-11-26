package com.possible.demo.injection

import com.possible.demo.StarterApplication
import com.possible.demo.injection.modules.ActivityBuilderModule
import com.possible.demo.injection.modules.ApiModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ApiModule::class, ActivityBuilderModule::class])
interface StarterAppComponent : AndroidInjector<StarterApplication>
