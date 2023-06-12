package com.example.composepractice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SampleEntity::class],
    version = 1,
)
abstract class SampleDatabase : RoomDatabase() {
    abstract val sampleDao: SampleDao
}