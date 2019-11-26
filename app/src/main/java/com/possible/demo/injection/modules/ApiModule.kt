package com.possible.demo.injection.modules

import com.possible.demo.data.remote.api.GithubUserService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class ApiModule {

    @Singleton
    @Provides
    fun providesGithubUserService(retrofit: Retrofit): GithubUserService = retrofit.create(GithubUserService::class.java)
}