package com.rafaelmfer.marvelcharactersconsultation.view

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rafaelmfer.marvelcharactersconsultation.R
import com.rafaelmfer.marvelcharactersconsultation.ViewModelFactory
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Result
import com.rafaelmfer.marvelcharactersconsultation.utils.changeVisibility
import com.rafaelmfer.marvelcharactersconsultation.utils.hideKeyboard
import com.rafaelmfer.marvelcharactersconsultation.utils.setStatusBarColor
import com.rafaelmfer.marvelcharactersconsultation.viewmodel.MarvelCharactersViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.card_character_profile.*
import kotlinx.android.synthetic.main.carousel_card_view_pager.*
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private val marvelCharactersViewModel by lazy {
        ViewModelProviders
            .of(this, ViewModelFactory())
            .get(MarvelCharactersViewModel::class.java)
    }

    private var homeAdapter = HomeAdapter()
    private var inputText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setStatusBarColor(R.color.colorPrimary, false)
        csViewPager.adapter = homeAdapter

        setObservers()
        setToolbarEndIconClick()
        setListenerKeyboardSearchClick()
    }

    private fun setListenerKeyboardSearchClick() {
        tieSearchToolbar.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    inputText = tilSearchToolbar.editText?.text.toString()
                    marvelCharactersViewModel.fetchCharactersList(inputText)
                    hideKeyboard()
                }
            }
            false
        }
    }

    private fun setToolbarEndIconClick() {
        tilSearchToolbar.setEndIconOnClickListener {
            tvErrorMessage.changeVisibility(false)
            inputText = tilSearchToolbar.editText?.text.toString()
            if (inputText != "") {
                marvelCharactersViewModel.fetchCharactersList(inputText)
            } else {
                tvErrorMessage.changeVisibility(true)
                clContent.changeVisibility(false)
            }
            hideKeyboard()
        }
    }

    private fun setObservers() {
        observerLoading()
        observerCharacter()
        observerComics()
        observerError()
    }

    private fun observerLoading() {
        marvelCharactersViewModel.command.observe(this, Observer {
            when (it) {
                is MarvelCharactersViewModel.Command.ShowLoading -> showLoading(true)
                is MarvelCharactersViewModel.Command.HideLoading -> showLoading(false)
            }
        })
    }

    private fun observerCharacter() {
        marvelCharactersViewModel.marvelCharacterResponse.observe(this, Observer { response ->
            if (response != null) {
                initViews(response)
                marvelCharactersViewModel.fetchComicsList(response.id)
            } else {
                tvErrorMessage.changeVisibility(true)
                clContent.changeVisibility(false)
            }
        })
    }

    private fun observerComics() {
        marvelCharactersViewModel.marvelComicsListResponse.observe(this, Observer { response ->
            response?.let { comicsData ->
                homeAdapter.run {
                    addComics(comicsData.results)
                    notifyDataSetChanged()
                }
            }
        })
    }

    private fun observerError() {
        marvelCharactersViewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(this, R.string.error_message, Toast.LENGTH_LONG).show()
        })
    }

    private fun initViews(character: Result) {
        tvCharacterName.text = character.name.toUpperCase(Locale.US)
        tvDescription.text = if (character.description.isNotEmpty()) character.description else getString(R.string.no_description)
        Glide.with(this)
            .load(character.thumbnail.path + "." + character.thumbnail.extension)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(ivCharacterImage)
    }

    private fun showLoading(visible: Boolean) {
        clContent.changeVisibility(!visible)
        progressCircular.changeVisibility(visible)
    }
}