package com.example.marvelapp.model

class MarvelImageUrl(
    val path: String,
    val extension: String
) {
    fun getImageUrl(): String {
        return path.replace("http", "https").plus("/standard_xlarge").plus(".").plus(extension)
    }

    fun getBigImageUrl(): String {
        return path.replace("http", "https").plus("/standard_fantastic").plus(".").plus(extension)
    }

}