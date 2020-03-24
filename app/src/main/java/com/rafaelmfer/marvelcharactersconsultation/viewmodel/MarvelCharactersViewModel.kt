package com.rafaelmfer.marvelcharactersconsultation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Data
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.MarvelApiResponse
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Result
import com.rafaelmfer.marvelcharactersconsultation.repository.MarvelRepository
import com.rafaelmfer.marvelcharactersconsultation.repository.MarvelServiceListener

class MarvelCharactersViewModel(
    private var marvelRepository: MarvelRepository
) : ViewModel(), MarvelServiceListener {

    sealed class Command {
        object ShowLoading : Command()
        object HideLoading : Command()
    }

    val command = MutableLiveData<Command>()
    val marvelCharacterResponse = MutableLiveData<List<Result>>()
    val marvelComicsListResponse = MutableLiveData<Data>()
    val errorLiveData = MutableLiveData<Throwable>()
    private var characterName = ""

    fun fetchCharactersList(charactersName: String) {
        characterName = charactersName
        command.value = Command.ShowLoading
        marvelRepository.fetchCharactersList(this, charactersName.substring(0, charactersName.length))
    }

    fun fetchComicsList(charactersId: Int) {
        command.value = Command.ShowLoading
        marvelRepository.fetchComicsList(this, charactersId)
    }


    override fun onSuccess(response: MarvelApiResponse) {
        command.value = Command.HideLoading
        marvelCharacterResponse.value = if (response.data.results.isNullOrEmpty()) null else response.data.results
    }

    override fun onSuccessComics(response: Data) {
        command.value = Command.HideLoading
        marvelComicsListResponse.value = response
    }

    override fun onError(error: Throwable) {
        command.value = Command.HideLoading
        errorLiveData.value = error
    }
}