package com.rafaelmfer.marvelcharactersconsultation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Data
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.MarvelApiResponse
import com.rafaelmfer.marvelcharactersconsultation.model.repository.MarvelRepository
import com.rafaelmfer.marvelcharactersconsultation.viewmodel.MarvelCharactersViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MarvelCharactersViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: MarvelRepository

    private lateinit var viewModel: MarvelCharactersViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MarvelCharactersViewModel(repository)
    }

    @Test
    fun fetchCharactersList_successResponse() {
        val response: MarvelApiResponse = mockk()
        val repositoriesList: Data? = null

        every { repository.fetchCharactersList(viewModel, "") } answers { viewModel.onSuccess(response) }
        if (repositoriesList != null) {
            every { response.data } returns repositoriesList
        }

        viewModel.fetchCharactersList("3")

        assertEquals(MarvelCharactersViewModel.Command.ShowLoading, viewModel.command.value)
        assertEquals(repositoriesList, viewModel.marvelCharacterResponse.value)
    }

    @Test
    fun fetchComicsList_successResponse() {
        val response: MarvelApiResponse = mockk()
        val comicsList: Data = mockk()

        every { repository.fetchComicsList(viewModel, 1) } answers { viewModel.onSuccessComics(comicsList) }
        every { response.data } returns comicsList

        viewModel.fetchComicsList(1)

        assertEquals(MarvelCharactersViewModel.Command.HideLoading, viewModel.command.value)
        assertEquals(comicsList, viewModel.marvelComicsListResponse.value)
    }

    @Test
    fun fetchCharactersList_errorResponse() {
        val error = Exception()

        every { repository.fetchCharactersList(viewModel, any()) } answers { viewModel.onError(error) }

        viewModel.fetchCharactersList("Iron Man")

        assertEquals(MarvelCharactersViewModel.Command.HideLoading, viewModel.command.value)
        assertEquals(error, viewModel.errorLiveData.value)
    }

    @Test
    fun fetchComicsList_errorResponse() {
        val error = Exception()

        every { repository.fetchComicsList(viewModel, any()) } answers { viewModel.onError(error) }

        viewModel.fetchComicsList(1)

        assertEquals(MarvelCharactersViewModel.Command.HideLoading, viewModel.command.value)
        assertEquals(error, viewModel.errorLiveData.value)
    }
}