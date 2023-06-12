package com.example.composepractice.domain.repository

import com.example.composepractice.domain.model.SampleModel

interface SampleRepository {
    suspend fun getSample(): SampleModel
}