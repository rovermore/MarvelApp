package com.example.marvelapp.model

import com.google.gson.annotations.SerializedName

data class DataResponse (
    @SerializedName("results")
    val results : List<Character>
)