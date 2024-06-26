package com.e_book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target  // Add this import for specifying image load targets

class ImageSliderAdapter(private val context: Context, private val imageList: List<Int>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {

    inner class ImageSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_slider, parent, false)
        return ImageSliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position])
            .override(Target.SIZE_ORIGINAL) // Optionally override size if needed
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
