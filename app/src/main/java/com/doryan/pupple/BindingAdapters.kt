package com.doryan.pupple

import android.text.format.DateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImage(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imgView.context)
                    .load(imgUri)
                    .into(imgView)
        }
    }

    @JvmStatic
    @BindingAdapter("dateFromTime")
    fun dateFromTime(textView: TextView, time: Long?) {
        time?.let {
            val text = DateFormat.format("yyyy/MM/dd", time)
            textView.text = text
        }
    }
}