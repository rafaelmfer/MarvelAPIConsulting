package com.rafaelmfer.marvelcharactersconsultation.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rafaelmfer.marvelcharactersconsultation.R
import com.rafaelmfer.marvelcharactersconsultation.ViewModelFactory
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Thumbnail
import com.rafaelmfer.marvelcharactersconsultation.utils.changeVisibility
import com.rafaelmfer.marvelcharactersconsultation.utils.setStatusBarColor
import com.rafaelmfer.marvelcharactersconsultation.utils.setToolbarAccessibleBackButton
import com.rafaelmfer.marvelcharactersconsultation.viewmodel.MarvelCharactersViewModel
import kotlinx.android.synthetic.main.activity_characters_details.*
import kotlinx.android.synthetic.main.activity_home.progressCircular
import kotlinx.android.synthetic.main.card_character_profile.*
import kotlinx.android.synthetic.main.carousel_card_view_pager.*
import java.util.Locale

class CharactersDetailsActivity : AppCompatActivity() {

    private val marvelCharactersViewModel by lazy {
        ViewModelProviders
            .of(this, ViewModelFactory())
            .get(MarvelCharactersViewModel::class.java)
    }

    private var charactersDetailsAdapter = CharactersDetailsAdapter()
    private var inputText: String = ""

    private var characterId = 0
    private lateinit var characterName: String
    private lateinit var characterDescription: String
    private lateinit var characterThumbnail: Thumbnail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_details)
        setStatusBarColor(R.color.colorPrimary, false)
        getIntentExtras()
        configureBackButton()
        csViewPager.adapter = charactersDetailsAdapter


        initViews(characterName, characterDescription, characterThumbnail)

        setObservers()
        marvelCharactersViewModel.fetchComicsList(characterId)
    }

    private fun configureBackButton() {
        ivBack.apply {
            setOnClickListener { onBackPressed() }
            setToolbarAccessibleBackButton()
        }
    }

    private fun getIntentExtras() {
        characterId = intent.getIntExtra("characterId", 0)
        characterName = intent.getStringExtra("characterName")
        characterDescription = intent.getStringExtra("characterDescription")
        characterThumbnail = intent.getParcelableExtra<Thumbnail>("characterThumbnail")
    }

    private fun setObservers() {
        observerLoading()
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

    private fun observerComics() {
        marvelCharactersViewModel.marvelComicsListResponse.observe(this, Observer { response ->
            response?.let { comicsData ->
                charactersDetailsAdapter.run {
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

    private fun initViews(characterName: String, characterDescription: String, image: Thumbnail) {
        tvCharacterName.text = characterName.toUpperCase(Locale.US)
        tvDescription.text = if (characterDescription.isNotEmpty()) characterDescription else getString(R.string.no_description)
        Glide.with(this)
            .load(image.path + "." + image.extension)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(ivCharacterImage)
    }

    private fun showLoading(visible: Boolean) {
        clContent.changeVisibility(!visible)
        progressCircular.changeVisibility(visible)
    }
}
