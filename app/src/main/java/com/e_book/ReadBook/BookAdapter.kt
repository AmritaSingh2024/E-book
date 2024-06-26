package com.e_book.ReadBook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e_book.R
import com.e_book.model.UserIdBookListResponseItem
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class BookAdapter(
    private var books: List<UserIdBookListResponseItem>,
    private val listener: OnItemClickListener // Interface instance
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    // Interface for item click handling
    interface OnItemClickListener {
        fun onItemClick(book: UserIdBookListResponseItem)
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookCoverImageView: ImageView = itemView.findViewById(R.id.bookCoverImageView)
        val bookTitleTextView: TextView = itemView.findViewById(R.id.bookname)
        val authorNameTextView: TextView = itemView.findViewById(R.id.authorname)
        val publishDateTextView: TextView = itemView.findViewById(R.id.publishDateTextView)
        val purchaseDateTextView: TextView = itemView.findViewById(R.id.purchaseDate)
        val buyNowButton: Button = itemView.findViewById(R.id.buyNowButton)


        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(books[position]) // Invoke interface method
                }
            }

            buyNowButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val book = books[position]
                    val downloadLink = book.downloadLink

                    // Pass downloadLink to ListBookActivity to handle click
                    listener.onItemClick(book)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val currentItem = books[position]

        // Load cover image using Picasso
        Picasso.get().load(currentItem.coverImageLink).into(holder.bookCoverImageView)

        // Display book details
        holder.bookTitleTextView.text = currentItem.bookName
        holder.authorNameTextView.text = currentItem.author

        // Format and display publication date
        holder.publishDateTextView.text = "Publish Date: ${currentItem.publicationDate}"

        // Extract date part from purchase date
        val purchaseDateTime = currentItem.purchaseDate
        if (purchaseDateTime.isNotEmpty()) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val parsedDate = dateFormat.parse(purchaseDateTime)
            parsedDate?.let {
                val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
                holder.purchaseDateTextView.text = "Purchase Date: $formattedDate"
            }
        }
    }

    override fun getItemCount() = books.size

    // Method to update data in the adapter
    fun updateData(newBooks: List<UserIdBookListResponseItem>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
