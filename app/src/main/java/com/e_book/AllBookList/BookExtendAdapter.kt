package com.e_book.AllBookList
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.e_book.LoginActivity
import com.e_book.R
import com.e_book.model.GetBookListResponseItem
import com.squareup.picasso.Picasso

class BookExtendAdapter(
    private var books: List<GetBookListResponseItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<BookExtendAdapter.BookExtendViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(book: GetBookListResponseItem)
    }
    inner class BookExtendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookCoverImageView: ImageView = itemView.findViewById(R.id.bookCoverImageView)
        val bookTitleTextView: TextView = itemView.findViewById(R.id.bookTitleTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.Author)
        val publicationDateTextView: TextView = itemView.findViewById(R.id.publishDate)
        val pricetextview: TextView = itemView.findViewById(R.id.price)
        val extendNowButton: Button = itemView.findViewById(R.id.extendNowButton)


        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(books[position])
                }
            }
            extendNowButton.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ExtendBuyNowActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookExtendViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_extend_item, parent, false)
        return BookExtendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookExtendViewHolder, position: Int) {
        val currentItem = books[position]
        holder.bookTitleTextView.text = currentItem.bookName
        holder.authorTextView.text = currentItem.author
        holder.publicationDateTextView.text = currentItem.publicationDate
        holder.pricetextview.text = "₹${currentItem.price}" // Adding currency symbol

        // Load cover image using Picasso
        Picasso.get()
            .load(currentItem.coverImageLink)
            .error(R.drawable.cover1) // Set default cover image here
            .into(holder.bookCoverImageView)
        Log.d("CoveImageLink", "CoveImageLink: " + currentItem.coverImageLink)
    }

    override fun getItemCount() = books.size

    fun setBooks(newBooks: List<GetBookListResponseItem>) {
        books = newBooks
        notifyDataSetChanged()
    }
}