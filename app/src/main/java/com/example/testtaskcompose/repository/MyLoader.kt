package com.example.testtaskcompose.repository

import com.example.testtaskcompose.models.GithubRepoInfo
import com.example.testtaskcompose.repository.RetrofitHelper.getRetrofit
import javax.inject.Inject


class MyLoader @Inject constructor(){
    suspend fun loadList(query: String): List<GithubRepoInfo> {
        val requestApiGithubRepos = getRetrofit().create(RequestApiGithubRepos::class.java)
        val response = requestApiGithubRepos.getRequest(query)
        response.body()?.map { it.login = query }
        return response.body() ?: emptyList()
    }
}