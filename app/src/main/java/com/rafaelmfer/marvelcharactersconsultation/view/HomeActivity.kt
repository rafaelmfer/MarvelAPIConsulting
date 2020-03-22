package com.rafaelmfer.marvelcharactersconsultation.view

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.islamkhsh.CardSliderViewPager
import com.rafaelmfer.marvelcharactersconsultation.R
import com.rafaelmfer.marvelcharactersconsultation.ViewModelFactory
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Result
import com.rafaelmfer.marvelcharactersconsultation.utils.changeVisibility
import com.rafaelmfer.marvelcharactersconsultation.viewmodel.MarvelCharactersViewModel
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    private lateinit var homeAdapter: HomeAdapter
    private val marvelCharactersViewModel by lazy {
        ViewModelProviders
            .of(this, ViewModelFactory())
            .get(MarvelCharactersViewModel::class.java)
    }
    private var inputText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setObservers()

        tilSearchToolbar.setEndIconOnClickListener {
            // Respond to end icon presses
            // Get input text
            inputText = tilSearchToolbar.editText?.text.toString()
            if (inputText != "") {
                marvelCharactersViewModel.fetchCharactersList(inputText)
            } else {
                Toast.makeText(this, R.string.error_message_empty_search, Toast.LENGTH_LONG).show()
            }
        }
        tieSearchToolbar.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    inputText = tilSearchToolbar.editText?.text.toString()
                    marvelCharactersViewModel.fetchCharactersList(inputText)
                }
            }
            false
        }
    }

    private fun setObservers() {
        marvelCharactersViewModel.command.observe(this, Observer {
            when (it) {
                is MarvelCharactersViewModel.Command.ShowLoading -> showLoading(true)
                is MarvelCharactersViewModel.Command.HideLoading -> showLoading(false)
            }
        })

        marvelCharactersViewModel.marvelCharacterResponse.observe(this, Observer { response ->
            homeAdapter = HomeAdapter()
            findViewById<CardSliderViewPager>(R.id.viewPager).adapter = homeAdapter
            initViews(response)
            marvelCharactersViewModel.fetchComicsList(response.id)
        })

        marvelCharactersViewModel.marvelComicsListResponse.observe(this, Observer { response ->
            response?.let { comicsData ->
                homeAdapter.run {
                    addComics(comicsData.results)
                    notifyDataSetChanged()
                }
            }
        })

        marvelCharactersViewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(this, R.string.error_message, Toast.LENGTH_LONG).show()
        })
    }

    private fun initViews(character: Result) {
        tvCharacterName.text = character.name
        tvDescription.text = character.description
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
