package com.example.composepractice.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SampleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSample(sampleEntity: SampleEntity)

    @Query("DELETE FROM samples")
    suspend fun clearSample()
}