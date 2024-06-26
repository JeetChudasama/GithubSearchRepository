package com.example.teachmintassignment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ScreenViewModel(
    var listener: RepoSearchListener = object : RepoSearchListener {
    override fun onSearchError(errorMessage: String) {
        // Default implementation for the listener
    }
}) : ViewModel() {
    var state by mutableStateOf(ScreenState())
    private var searchJob: Job? = null

    private val gitHubService = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubService::class.java)

    fun onAction(userAction: UserAction){
        when (userAction){

            UserAction.CLoseIconClicked -> {
                state = state.copy(isSearchBarVisible = false)
            }
            UserAction.SearchIconClicked -> {
                state = state.copy(isSearchBarVisible = true)
            }
            is UserAction.TextFieldInput -> {
                state = state.copy(searchText = userAction.text)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    searchRepoInList(searchQuery = userAction.text)
                }
            }
        }
    }
    private fun searchRepoInList(
        searchQuery: String
    ) {
        viewModelScope.launch{
            try {
                val repos = gitHubService.searchRepos(searchQuery)
                state = state.copy(repoList = repos.items)
            } catch (e: Exception) {
                listener.onSearchError("Error searching repositories: ${e.message}")
            }
        }
    }
}

sealed class UserAction {
    object SearchIconClicked: UserAction()
    object CLoseIconClicked: UserAction()
    data class TextFieldInput(val text: String): UserAction()
}

data class ScreenState(
    val searchText: String = "",
    val isSearchBarVisible: Boolean = false,
    val repoList: List<GitHubRepo> = emptyList()
)

///