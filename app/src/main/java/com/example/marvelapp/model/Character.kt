package com.example.marvelapp.model

import com.google.gson.annotations.SerializedName

data class Character (
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("thumbnail")
    val image: MarvelImageUrl?
)