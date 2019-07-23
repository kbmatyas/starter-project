package com.possible.demo.utilities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("avatarUrl")
fun ImageView.setAvatar(url: String) {
    Picasso.with(context).load(url).into(this)
}