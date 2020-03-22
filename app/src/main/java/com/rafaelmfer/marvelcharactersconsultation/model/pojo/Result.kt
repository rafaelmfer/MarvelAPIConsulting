package com.rafaelmfer.marvelcharactersconsultation.model.pojo

import com.google.gson.annotations.SerializedName
import com.rafaelmfer.marvelcharactersconsultation.model.comic.Characters
import com.rafaelmfer.marvelcharactersconsultation.model.comic.Creators
import com.rafaelmfer.marvelcharactersconsultation.model.comic.Date
import com.rafaelmfer.marvelcharactersconsultation.model.comic.ImageComic
import com.rafaelmfer.marvelcharactersconsultation.model.comic.Price
import com.rafaelmfer.marvelcharactersconsultation.model.comic.TextObject

data class Result(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("modified") val modified: String,
    @SerializedName("thumbnail") val thumbnail: Thumbnail,
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("comics") val comics: Comics,
    @SerializedName("series") val series: Series,
    @SerializedName("stories") val stories: Stories,
    @SerializedName("events") val events: Events,
    @SerializedName("urls") val urls: List<Url>,


    //Chaves Extras da Chamada '/{characterId}/Comic'
    @SerializedName("digitalId") val digitalId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("issueNumber") val issueNumber: Int,
    @SerializedName("variantDescription") val variantDescription: String,
    @SerializedName("isbn") val isbn: String,
    @SerializedName("upc") val upc: String,
    @SerializedName("diamondCode") val diamondCode: String,
    @SerializedName("ean") val ean: String,
    @SerializedName("issn") val issn: String,
    @SerializedName("format") val format: String,
    @SerializedName("pageCount") val pageCount: Int,
    @SerializedName("textObjects") val textObjects: List<TextObject>,
    @SerializedName("variants") val variants: List<Any>,
    @SerializedName("collections") val collections: List<Any>,
    @SerializedName("collectedIssues") val collectedIssues: List<Any>,
    @SerializedName("dates") val dates: List<Date>,
    @SerializedName("prices") val prices: List<Price>,
    @SerializedName("images") val images: List<ImageComic>,
    @SerializedName("creators") val creators: Creators,
    @SerializedName("characters") val characters: Characters
)