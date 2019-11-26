package com.possible.demo.feature.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.possible.demo.data.DataResult
import com.possible.demo.data.remote.repository.GitHubRepoRepository
import com.possible.demo.data.model.Repository
import com.possible.demo.feature.base.BaseViewModel
import kotlinx.coroutines.launch

class RepositoryListViewModel(private val userName: String, private val repository: GitHubRepoRepository) : BaseViewModel() {

    private val screenState = MutableLiveData<ScreenState>()
    val isLoading: LiveData<Boolean> = Transformations.map(screenState) { it == ScreenState.LOADING }
    val isEmpty: LiveData<Boolean> = Transformations.map(screenState) { it == ScreenState.EMPTY }
    val isLoaded: LiveData<Boolean> = Transformations.map(screenState) { it == ScreenState.NORMAL }
    val isError: LiveData<Boolean> = Transformations.map(screenState) { it == ScreenState.ERROR }

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories

    init {
        load()
    }

    fun load() {

        launch {

            screenState.value = ScreenState.LOADING

            when (val result = repository.getUserRepositories(userName)) {
                is DataResult.Success -> {
                    screenState.value = if (result.value.isEmpty()) ScreenState.EMPTY else ScreenState.NORMAL
                    _repositories.value = result.value
                }
                is DataResult.Error -> {
                    screenState.value = ScreenState.ERROR
                }
            }
        }
    }

    enum class ScreenState {
        LOADING,
        EMPTY,
        NORMAL,
        ERROR
    }
}