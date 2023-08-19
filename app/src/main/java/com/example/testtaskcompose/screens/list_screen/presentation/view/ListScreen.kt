package com.example.testtaskcompose.screens.list_screen.presentation.view

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testtaskcompose.R
import com.example.testtaskcompose.models.GithubRepoInfo
import com.example.testtaskcompose.screens.list_screen.presentation.intent.ListIntent
import com.example.testtaskcompose.screens.list_screen.presentation.viewmodel.ListViewModel
import com.example.testtaskcompose.screens.list_screen.presentation.viewstate.ListState

@Composable
fun ListScreen() {
    val viewModel: ListViewModel = viewModel()
    val listState by viewModel.list.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTextField(onSearch = { intent -> viewModel.handleIntent(intent)})
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (listState) {
                is ListState.Idle -> { MyToast("Нет данных по этому имени") }
                is ListState.Loading -> { CircularProgressIndicator() }
                is ListState.Repos -> { MyList((listState as ListState.Repos).repos) }
                is ListState.Error -> {
                    MyToast((listState as ListState.Error).error ?: "Непредвиденная ошибка") }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(onSearch: (intent: ListIntent) -> Unit) {
    val textValue = remember { mutableStateOf("") }
    val primaryColor = colorResource(id = R.color.black)
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = stringResource(R.string.input_username)) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = primaryColor,
            focusedLabelColor = primaryColor,
            cursorColor = primaryColor
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onSearch(ListIntent.FetchUser(textValue.value))


            }),
        value = textValue.value,
        onValueChange = { textValue.value = it },
        singleLine = true

    )
}

@Composable
fun MyList(repos: List<GithubRepoInfo>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(repos) {
            Text(text = it.name ?: "")
        }
    }
}

@Composable
private fun MyToast(message: String){
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG).show()
}

@Preview
@Composable
fun PreviewScreen() {
    ListScreen()
}