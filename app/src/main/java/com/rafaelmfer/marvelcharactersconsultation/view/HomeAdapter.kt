package com.rafaelmfer.marvelcharactersconsultation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rafaelmfer.marvelcharactersconsultation.R
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Result

class HomeAdapter(private val listener: OnClickListenerMarvelCharacter) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var charactersList = listOf<Result>()

    fun addCharactersList(items: List<Result>) {
        charactersList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_character_item, parent, false)
    )

    override fun getItemCount() = charactersList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = charactersList[position]

        holder.apply {
            tvCharactersNameHomeList.text = items.name
            itemView.setOnClickListener {
                listener.onClickCharacterId(items)
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCharactersNameHomeList: TextView = itemView.findViewById(R.id.tvCharactersNameHomeList)

    }
}