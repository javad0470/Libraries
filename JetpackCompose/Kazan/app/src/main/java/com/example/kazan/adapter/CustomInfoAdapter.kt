package com.example.kazan.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.example.kazan.R
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.Marker

class CustomInfoAdapter(
    context: Context,
) : GoogleMap.InfoWindowAdapter {

    private val contentView = (context as Activity).layoutInflater
        .inflate(R.layout.custom_info_window,null)

    override fun getInfoWindow(marker: Marker): View? {
        rendersViews(marker = marker,contentView = contentView)
        return contentView
    }

    override fun getInfoContents(marker: Marker): View? {
        rendersViews(marker = marker,contentView = contentView)
        return contentView
    }

    private fun rendersViews(marker: Marker?,contentView:View){
        val title = marker?.title
        val description = marker?.snippet

        val titleTextView = contentView.findViewById<TextView>(R.id.title_textView)
        titleTextView.text = title

        val descriptionTextView = contentView.findViewById<TextView>(R.id.description_textView)
        descriptionTextView.text = description

        val image = contentView.findViewById<ImageView>(R.id.img_test)
        image.load("https://www.pixsy.com/wp-content/uploads/2021/04/ben-sweet-2LowviVHZ-E-unsplash-1.jpeg")
    }
}