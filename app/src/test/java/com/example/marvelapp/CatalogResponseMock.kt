package com.example.marvelapp

import com.example.marvelapp.model.CatalogResponse
import com.example.marvelapp.model.Character
import com.example.marvelapp.model.DataResponse
import com.example.marvelapp.model.MarvelImageUrl

object CatalogResponseMock {
    private val characterList = listOf<Character>(
        Character(
            id = 100,
            name = "Captain America",
            description = "Also the captain of The Avengers",
            image = MarvelImageUrl(
                        "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                "jpg"
            )
        ),
        Character(
            id = 101,
            name = "Spiderman",
            description = "Got his powers from a spider bite",
            image = MarvelImageUrl(
                "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16",
                "jpg"
            )
        ),
        Character(
            id = 102,
            name = "Hulk",
            description = "Aslo known as scientist Bruce Banner",
            image = MarvelImageUrl(
                "http://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec",
                "jpg"
            )
        )
    )
    private val dataResponse = DataResponse(characterList)
    val catalogResponse = CatalogResponse(dataResponse)

    private val emptyCharacterList = listOf<Character>()
    private val emptyDataResponse = DataResponse(emptyCharacterList)
    val emptyCatalogResponse = CatalogResponse(emptyDataResponse)

    private val detailCharacterList = listOf<Character>(
        Character(
            id = 100,
            name = "Captain America",
            description = "Also the captain of The Avengers",
            image = MarvelImageUrl(
                "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
                "jpg"
            )
        )
    )
    private val detailDataResponse = DataResponse(detailCharacterList)
    val detailResponse = CatalogResponse(detailDataResponse)

}