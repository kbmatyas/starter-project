package com.possible.demo.feature.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.possible.demo.R
import com.possible.demo.data.DataResult
import com.possible.demo.data.model.User
import com.possible.demo.data.remote.repository.FollowerRepository
import com.possible.demo.feature.base.BaseViewModel
import com.possible.demo.shared.debounce
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FollowerListViewModel(private val repository: FollowerRepository) : BaseViewModel() {

    private val screenState = MutableLiveData<ScreenState>().apply { value = ScreenState.MISSING_SEARCH_TERM }
    val isLoading: LiveData<Boolean> = Transformations.map(screenState) { it == ScreenState.LOADING }
    val isLoaded: LiveData<Boolean> = Transformations.map(screenState) { it == ScreenState.NORMAL }
    val isSearchTermMissing: LiveData<Boolean> = Transformations.map(screenState) { it == ScreenState.MISSING_SEARCH_TERM }
    val isError: LiveData<Boolean> = Transformations.map(screenState) { it == ScreenState.ERROR }

    private val _errorText = MutableLiveData<Int>().apply { value = R.string.nothing }
    val errorText: LiveData<Int> get() = _errorText
    private val _errorActionButtonVisible = MutableLiveData<Boolean>()
    val errorActionButtonVisible: LiveData<Boolean> get() = _errorActionButtonVisible

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    val searchText = MutableLiveData<String>()
    private val debouncedSearchText = searchText.debounce(DEBOUNCE_DURATION)

    private val searchTextObserver: Observer<String> = Observer { text ->
        if (text.length >= MIN_SEARCH_TERM_LENGTH) {
            loadFollowersForUser(text)
        } else {
            _users.value = emptyList()
            screenState.value = ScreenState.EMPTY
        }
    }

    private var loadJob: Job? = null

    init {
        debouncedSearchText.observeForever(searchTextObserver)
    }

    override fun onCleared() {
        super.onCleared()
        debouncedSearchText.removeObserver(searchTextObserver)
    }

    private fun loadFollowersForUser(searchText: String) {

        loadJob?.cancel()
        loadJob = launch {

            screenState.value = ScreenState.LOADING

            when (val result = repository.getFollowersForUser(searchText)) {
                is DataResult.Success -> {
                    screenState.value = ScreenState.NORMAL
                    _users.value = result.value
                }
                is DataResult.Error -> {
                    prepareErrorFieldsForError(result.throwable)
                    screenState.value = ScreenState.ERROR
                }
            }
        }
    }

    private fun prepareErrorFieldsForError(error: FollowerRepository.FollowerRequestError) {
        when (error) {
            FollowerRepository.FollowerRequestError.NotFound -> {
                _errorText.value = R.string.user_not_found
                _errorActionButtonVisible.value = false
            }
            FollowerRepository.FollowerRequestError.NetworkError -> {
                _errorText.value = R.string.no_connection
                _errorActionButtonVisible.value = true
            }
            FollowerRepository.FollowerRequestError.GeneralError -> {
                _errorText.value = R.string.repository_list_load_error
                _errorActionButtonVisible.value = true
            }
        }
    }

    /**
     * Retries the failed query if the current search term permits the request.
     */
    fun retry() {
        if (searchText.value?.length ?: 0 > MIN_SEARCH_TERM_LENGTH) {
            loadFollowersForUser(searchText.value.orEmpty())
        }
    }

    enum class ScreenState {
        MISSING_SEARCH_TERM,
        LOADING,
        NORMAL,
        EMPTY,
        ERROR
    }

    companion object {
        private const val DEBOUNCE_DURATION = 300L
        private const val MIN_SEARCH_TERM_LENGTH = 2
    }
}