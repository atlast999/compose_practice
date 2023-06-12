package com.example.composepractice.base.serialize

import com.google.gson.Gson
import com.google.gson.GsonBuilder

interface Serializer {

}

class GsonSerializer : Serializer {

    companion object {
        fun provideGson(): Gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }
}