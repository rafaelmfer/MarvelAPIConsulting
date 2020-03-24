package com.rafaelmfer.marvelcharactersconsultation.view

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafaelmfer.marvelcharactersconsultation.R
import com.rafaelmfer.marvelcharactersconsultation.ViewModelFactory
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Result
import com.rafaelmfer.marvelcharactersconsultation.utils.changeVisibility
import com.rafaelmfer.marvelcharactersconsultation.utils.hideKeyboard
import com.rafaelmfer.marvelcharactersconsultation.utils.setStatusBarColor
import com.rafaelmfer.marvelcharactersconsultation.viewmodel.MarvelCharactersViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val marvelCharactersViewModel by lazy {
        ViewModelProviders
            .of(this, ViewModelFactory())
            .get(MarvelCharactersViewModel::class.java)
    }

    private lateinit var homeAdapter: HomeAdapter
    private var inputText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setStatusBarColor(R.color.colorPrimary, false)

        setList()
        setObservers()
        setToolbarEndIconClick()
        setListenerKeyboardSearchClick()
    }

    private val listener = object : OnClickListenerMarvelCharacter {
        override fun onClickCharacterId(characterResult: Result) {
            startActivity(Intent(this@HomeActivity, CharactersDetailsActivity::class.java).apply {
                putExtra("characterId", characterResult.id)
                putExtra("characterName", characterResult.name)
                putExtra("characterDescription", characterResult.description)
                putExtra("characterThumbnail", characterResult.thumbnail)
            })
        }
    }

    private fun setList() {
        rvCharactersNameList.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            homeAdapter = HomeAdapter(listener)
            adapter = homeAdapter
        }
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
                rvCharactersNameList.changeVisibility(false)
            }
            hideKeyboard()
        }
    }

    private fun setObservers() {
        observerLoading()
        observerCharacter()
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
                homeAdapter.run {
                    addCharactersList(response)
                    notifyDataSetChanged()
                }
            } else {
                tvErrorMessage.changeVisibility(true)
                rvCharactersNameList.changeVisibility(false)
            }
        })
    }

    private fun observerError() {
        marvelCharactersViewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(this, R.string.error_message, Toast.LENGTH_LONG).show()
        })
    }

    private fun showLoading(visible: Boolean) {
        rvCharactersNameList.changeVisibility(!visible)
        progressCircular.changeVisibility(visible)
    }
}
