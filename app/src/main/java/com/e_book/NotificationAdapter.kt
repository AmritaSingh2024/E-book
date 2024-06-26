package com.e_book

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e_book.model.NotificationResponseItem
import com.squareup.picasso.Picasso

class NotificationAdapter(
    private val notifications: List<NotificationResponseItem>,
    private val onNotificationClick: (Int) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        holder.messageTextView.text = notification.message

        // Correcting the file path format
        val correctedFilePath = notification.filePath?.replace("\\", "/")
        val imageUrl = when {
            !correctedFilePath.isNullOrEmpty() -> correctedFilePath
            !notification.imagePath.isNullOrEmpty() -> notification.imagePath
            else -> null
        }

        imageUrl?.let {
            Picasso.get()
                .load(it)
                .error(R.drawable.logolua)
                .into(holder.imageView)
        } ?: run {
            holder.imageView.setImageResource(R.drawable.logolua)
        }

        Log.d("filepath", "filepath: $correctedFilePath")
        holder.itemView.setOnClickListener {
            onNotificationClick(position)
        }
    }

    override fun getItemCount(): Int = notifications.size
}
