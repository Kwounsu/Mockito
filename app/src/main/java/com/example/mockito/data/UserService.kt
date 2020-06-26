package com.example.mockito.data

import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("user/{user}/repos")
    fun getRepositories(@Path("user") githubUsername: String) : Maybe<List<Repository>>
}