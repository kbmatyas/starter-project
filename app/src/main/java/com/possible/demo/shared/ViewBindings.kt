package com.possible.demo.shared

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.possible.demo.R
import com.squareup.picasso.Picasso

@BindingAdapter("isVisible")
fun View.setIsVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("avatarUrl")
fun ImageView.setAvatarUrl(avatarUrl: String?) {
    Picasso.with(this.context).load(avatarUrl).placeholder(R.drawable.ic_account_circle_24dp).transform(PicassoCircleTransformation()).into(this)
}