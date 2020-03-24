package com.rafaelmfer.marvelcharactersconsultation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rafaelmfer.marvelcharactersconsultation.repository.MarvelRepository
import com.rafaelmfer.marvelcharactersconsultation.viewmodel.MarvelCharactersViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MarvelCharactersViewModel::class.java) ->
                    MarvelCharactersViewModel(MarvelRepository())
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}