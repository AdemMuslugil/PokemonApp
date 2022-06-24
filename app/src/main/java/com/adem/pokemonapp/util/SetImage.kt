package com.adem.pokemonapp.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.adem.pokemonapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.downloadImage(url : String, progressBar: CircularProgressDrawable){

    val option = RequestOptions()
        .placeholder(progressBar)
        .error(R.mipmap.ic_launcher_round)

    Glide
        .with(context)
        .setDefaultRequestOptions(option)
        .load(url)
        .into(this)

}


fun placeHolderProgressBar(context: Context) : CircularProgressDrawable{

    return CircularProgressDrawable(context).apply {
        strokeWidth = 3f
        centerRadius = 15f
        start()
    }
}