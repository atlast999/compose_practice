package com.example.composepractice.presentation.sample_screen

import com.example.composepractice.domain.model.SampleModel

data class SampleScreenState(
    val sampleData: SampleModel? = null,
    val isLoading: Boolean = false
)

sealed class SampleScreenEvent {
    object Load : SampleScreenEvent()
}