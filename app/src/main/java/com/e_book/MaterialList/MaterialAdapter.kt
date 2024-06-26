package com.e_book.MaterialList
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e_book.R
import com.e_book.model.GetMaterialResponseItem
import com.squareup.picasso.Picasso

class MaterialAdapter(
    private var books: List<GetMaterialResponseItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MaterialAdapter.BookMaterialViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(book: GetMaterialResponseItem)
    }

    inner class BookMaterialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookCoverListImageView: ImageView = itemView.findViewById(R.id.booklistcoverImageView)
        val bookNameTextView: TextView = itemView.findViewById(R.id.listbookname)
        val authorTextView: TextView = itemView.findViewById(R.id.listauthorname)
        val publicationDateTextView: TextView = itemView.findViewById(R.id.listpublishDateTextView)
        val listViewnow: Button = itemView.findViewById(R.id.listViewNow)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(books[position])
                }
            }

            listViewnow.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = books[position]
                    val context = itemView.context

                    // Start PDFViewActivity and pass the URL
                    val intent = Intent(context, PDFViewActivity::class.java)
                    intent.putExtra("FILE_URL", currentItem.downloadLink)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMaterialViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.material_item, parent, false)
        return BookMaterialViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookMaterialViewHolder, position: Int) {
        val currentItem = books[position]
        holder.bookNameTextView.text = currentItem.bookName
        holder.authorTextView.text = currentItem.author
        holder.publicationDateTextView.text = currentItem.publicationDate

        // Load cover image using Picasso
        Picasso.get()
            .load(currentItem.coverImageLink)
            .error(R.drawable.logolu) // Set default cover image here
            .into(holder.bookCoverListImageView)
        Log.d("CoverImageLink", "CoverImageLink: " + currentItem.coverImageLink)
    }

    override fun getItemCount() = books.size

    fun setBooks(newBooks: List<GetMaterialResponseItem>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
