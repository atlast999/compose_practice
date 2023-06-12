package com.example.composepractice.di

import android.content.Context
import androidx.room.Room
import com.example.composepractice.base.network.buildRetrofit
import com.example.composepractice.base.serialize.GsonSerializer
import com.example.composepractice.base.serialize.Serializer
import com.example.composepractice.data.local.SampleDatabase
import com.example.composepractice.data.remote.ApiService
import com.example.composepractice.data.repository.SampleRepositoryImpl
import com.example.composepractice.domain.repository.SampleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppComponents {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = buildRetrofit(
        baseUrl = "http://example.com"
    ).create()

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): SampleDatabase = Room.databaseBuilder(
        context = context,
        klass = SampleDatabase::class.java,
        name = "sample_database",
    ).build()

    @Provides
    @Singleton
    fun provideSerializer(): Serializer = GsonSerializer()

    @Provides
    @Singleton
    fun provideRepository(
        apiService: ApiService,
        database: SampleDatabase,
    ): SampleRepository = SampleRepositoryImpl(
        apiService = apiService,
        database = database,
    )


}