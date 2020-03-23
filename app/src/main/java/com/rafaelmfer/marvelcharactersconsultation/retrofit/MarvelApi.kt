package com.rafaelmfer.marvelcharactersconsultation.retrofit

import com.rafaelmfer.marvelcharactersconsultation.model.pojo.MarvelApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun fetchCharactersList(
        @Query("nameStartsWith") charactersInitialLetter: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
//        @Query("offset") offset: Int,
        @Query("apikey") apiKey: String,
        @Query("limit") limit: Int = 100
    ): Call<MarvelApiResponse>

    @GET("characters/{characterId}/comics")
    fun getComicsList(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("apikey") apiKey: String,
        @Query("limit") limit: Int = 100
    ): Call<MarvelApiResponse>


}