package com.example.testtaskcompose.repository

import com.example.testtaskcompose.models.GithubRepoInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestApiGithubRepos {
    @GET("users/{username}/repos")
    suspend fun getRequest(@Path("username") username: String): Response<List<GithubRepoInfo>>
}