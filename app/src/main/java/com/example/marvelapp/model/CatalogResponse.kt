package com.example.marvelapp.model

import com.example.marvelapp.model.DataResponse
import com.google.gson.annotations.SerializedName

data class CatalogResponse(
    @SerializedName("data")
    val data: DataResponse?
) {
}