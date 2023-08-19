package com.example.testtaskcompose.screens.list_screen.presentation.viewstate

import com.example.testtaskcompose.models.GithubRepoInfo

sealed class ListState {
    object Idle : ListState()
    object Loading : ListState()
    data class Repos(val repos: List<GithubRepoInfo>) : ListState()
    data class Error(val error: String?) : ListState()
}
