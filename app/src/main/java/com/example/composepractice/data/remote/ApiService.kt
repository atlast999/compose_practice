package com.example.composepractice.data.remote

import com.example.composepractice.data.remote.dto.SampleDto
import retrofit2.http.GET

interface ApiService {

    @GET("sample_endpoint")
    suspend fun getSample(): SampleDto
}