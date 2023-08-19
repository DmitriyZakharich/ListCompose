package com.example.testtaskcompose.screens.list_screen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtaskcompose.repository.MyLoader
import com.example.testtaskcompose.screens.list_screen.presentation.intent.ListIntent
import com.example.testtaskcompose.screens.list_screen.presentation.viewstate.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val myLoader: MyLoader
) : ViewModel() {

    private val _list: MutableStateFlow<ListState> = MutableStateFlow(ListState.Idle)
    val list: StateFlow<ListState> = _list


    fun handleIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.FetchUser -> getList(intent.query)
        }
    }

    private fun getList(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _list.emit(ListState.Loading)
            _list.emit(try {
                val list = myLoader.loadList(query)
                if (list.isNotEmpty()) {
                    ListState.Repos(list)
                } else {
                    ListState.Idle
                }
            } catch (e: Exception) {
                ListState.Error(e.localizedMessage)
            })
        }
    }
}