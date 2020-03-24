package com.rafaelmfer.marvelcharactersconsultation.model.repository

import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Data
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.MarvelApiResponse

interface MarvelServiceListener {

    fun onSuccess(response: MarvelApiResponse)

    fun onSuccessComics(response: Data)

    fun onError(error: Throwable)
}
