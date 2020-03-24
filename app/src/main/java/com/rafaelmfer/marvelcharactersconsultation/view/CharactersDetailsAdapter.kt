package com.rafaelmfer.marvelcharactersconsultation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.islamkhsh.CardSliderAdapter
import com.rafaelmfer.marvelcharactersconsultation.R
import com.rafaelmfer.marvelcharactersconsultation.model.pojo.Result

class CharactersDetailsAdapter : CardSliderAdapter<CharactersDetailsAdapter.MarvelViewHolder>() {

    private var comicListData = listOf<Result>()

    fun addComics(comics: List<Result>) {
        comicListData = comics
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MarvelViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.carousel_item, parent, false)
    )

    override fun getItemCount() = comicListData.size

    override fun bindVH(holder: MarvelViewHolder, position: Int) {
        comicListData[position].run {

            holder.apply {
                tvComicTitle.text = title
                Glide.with(itemView)
                    .load(thumbnail.path + "." + thumbnail.extension)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(ivComicCarousel)
            }
        }
    }

    class MarvelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivComicCarousel: ImageView = itemView.findViewById(R.id.ivComicCarousel)
        val tvComicTitle: TextView = itemView.findViewById(R.id.tvComicTitle)
    }
}