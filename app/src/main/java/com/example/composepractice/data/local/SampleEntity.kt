package com.example.composepractice.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.composepractice.domain.model.SampleModel

@Entity(tableName = "samples")
data class SampleEntity(
    @PrimaryKey val sampleKey: Int? = null,
    @ColumnInfo(name = "sample_field") val sampleField: String,
)

fun SampleEntity.toModel() = SampleModel(
    sampleField = sampleField
)

fun SampleModel.toEntity() = SampleEntity(
    sampleField = sampleField,
)