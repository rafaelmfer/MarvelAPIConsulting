package com.rafaelmfer.marvelcharactersconsultation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Data
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.MarvelApiResponse
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Result
import com.rafaelmfer.marvelcharactersconsultation.model.repository.MarvelRepository
import com.rafaelmfer.marvelcharactersconsultation.model.repository.MarvelServiceListener

class MarvelCharactersViewModel(
    private var marvelRepository: MarvelRepository
) : ViewModel(), MarvelServiceListener {

    sealed class Command {
        object ShowLoading : Command()
        object HideLoading : Command()
    }

    val command = MutableLiveData<Command>()
    val marvelCharacterResponse = MutableLiveData<Result>()
    val marvelComicsListResponse = MutableLiveData<Data>()
    val errorLiveData = MutableLiveData<Throwable>()
    private var characterName = ""

    fun fetchCharactersList(charactersName: String) {
        characterName = charactersName
        command.value = Command.ShowLoading
        marvelRepository.fetchCharactersList(this)
    }

    fun fetchComicsList(charactersId: Int) {
        marvelRepository.fetchComicsList(this, charactersId)
    }


    override fun onSuccess(response: MarvelApiResponse) {
        command.value = Command.HideLoading

        response.data.results.forEach { result ->
            if (result.name.contentEquals(characterName)) {
                marvelCharacterResponse.value = result
                return@forEach
            }
        }
    }

    override fun onSuccessComics(response: Data) {
        marvelComicsListResponse.value = response
    }

    override fun onError(error: Throwable) {
        command.value = Command.HideLoading
        errorLiveData.value = error
    }
}