package com.example.marvelapp

import com.example.marvelapp.model.MarvelImageUrl
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class MarvelImageUrlTest {

    lateinit var marvelImageUrl: MarvelImageUrl

    private val fixedMarvelUrl = MarvelImageUrlMock.fixedMarvelUrl
    private val fixedBigMarvelUrl = MarvelImageUrlMock.fixedBigMarvelUrl

    @Before
    fun setup() {
        marvelImageUrl = MarvelImageUrlMock.marvelImageUrl
    }

    @Test
    fun `check if getImageUrl function works`() {
        val returnedImageUrl = marvelImageUrl.getImageUrl()
        TestCase.assertEquals(returnedImageUrl, fixedMarvelUrl)
    }

    @Test
    fun `check if getBigImageUrl function works`() {
        val returnedImageUrl = marvelImageUrl.getBigImageUrl()
        TestCase.assertEquals(returnedImageUrl, fixedBigMarvelUrl)
    }
}