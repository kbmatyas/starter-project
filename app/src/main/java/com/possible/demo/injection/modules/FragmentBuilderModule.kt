package com.possible.demo.injection.modules

import com.possible.demo.feature.users.fragment.RepositoryListFragment
import com.possible.demo.feature.users.fragment.FollowerListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeFollowerListFragment(): FollowerListFragment

    @ContributesAndroidInjector
    abstract fun contributeRepositoryListFragment(): RepositoryListFragment
}