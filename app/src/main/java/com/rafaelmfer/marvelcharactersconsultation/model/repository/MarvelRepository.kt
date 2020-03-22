package com.rafaelmfer.marvelcharactersconsultation.model.repository

import com.rafaelmfer.marvelcharactersconsultation.model.pojo.MarvelApiResponse
import com.rafaelmfer.marvelcharactersconsultation.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MarvelRepository {

    private val serviceApi = RetrofitService.getService()
    private val ts = System.currentTimeMillis().toString()
    private val hash = getMd5(ts)

    fun fetchCharactersList(listener: MarvelServiceListener) {

        serviceApi.fetchCharactersList(ts, hash, PUBLIC_KEY).enqueue(object : Callback<MarvelApiResponse>{

            override fun onResponse(call: Call<MarvelApiResponse>, response: Response<MarvelApiResponse>) {
                response.body()?.let {
                    listener.onSuccess(it)
                } ?: listener.onError(Exception())
            }

            override fun onFailure(call: Call<MarvelApiResponse>, t: Throwable) {
                listener.onError(t)
            }
        })
    }

    fun fetchComicsList(listener: MarvelServiceListener, characterId: Int) {

        serviceApi.getComicsList(characterId, ts, hash, PUBLIC_KEY).enqueue(object : Callback<MarvelApiResponse>{

            override fun onResponse(call: Call<MarvelApiResponse>, response: Response<MarvelApiResponse>) {
                response.body()?.let {
                    listener.onSuccessComics(it.data)
                } ?: listener.onError(Exception())
            }

            override fun onFailure(call: Call<MarvelApiResponse>, t: Throwable) {
                listener.onError(t)
            }
        })
    }

    private fun getMd5(ts: String): String {
        try {
            val md = MessageDigest.getInstance("MD5")

            val messageDigest = md.digest(ts.toByteArray()
                    + PRIVATE_KEY.toByteArray()
                    + PUBLIC_KEY.toByteArray())

            val no = BigInteger(1, messageDigest)

            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            return hashtext
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }

    companion object {
        private const val PUBLIC_KEY = "dd82cc3630e1624e9695dbd63c236c72"
        private const val PRIVATE_KEY = "3065762afd1a9acaeb446aa6195e5a92f2a58c69"
    }
}