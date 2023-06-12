package com.example.composepractice.data.remote.dto

import com.example.composepractice.data.local.SampleEntity
import com.example.composepractice.domain.model.SampleModel
import com.google.gson.annotations.SerializedName

class SampleDto(
    @SerializedName("sample_field") val sampleField: String,
)

fun SampleDto.toModel() = SampleModel(
    sampleField = sampleField
)

fun SampleDto.toEntity() = SampleEntity(
    sampleField = sampleField,
)