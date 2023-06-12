package com.example.composepractice.data.repository

import com.example.composepractice.data.local.SampleDatabase
import com.example.composepractice.data.remote.ApiService
import com.example.composepractice.data.remote.dto.toModel
import com.example.composepractice.domain.model.SampleModel
import com.example.composepractice.domain.repository.SampleRepository

class SampleRepositoryImpl(
    private val apiService: ApiService,
    database: SampleDatabase,
) : SampleRepository {
    private val dao = database.sampleDao

    override suspend fun getSample(): SampleModel {
        return apiService.getSample().toModel()
    }
}