package com.example.composepractice.presentation.sample_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composepractice.domain.model.SampleModel
import com.example.composepractice.domain.repository.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val repository: SampleRepository,
) : ViewModel() {
    var screenState by mutableStateOf(SampleScreenState())

    fun dispatchEvent(event: SampleScreenEvent) {
        when (event) {
            SampleScreenEvent.Load -> loadData()
        }
    }

    private fun loadData() {
        screenState = screenState.copy(
            isLoading = true,
        )
        viewModelScope.launch {
            delay(2000)
            try {
                repository.getSample().let {
                    screenState = screenState.copy(
                        sampleData = it,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                screenState = screenState.copy(
                    sampleData = SampleModel(sampleField = "Hello there"),
                    isLoading = false,
                )
            }

        }
    }
}