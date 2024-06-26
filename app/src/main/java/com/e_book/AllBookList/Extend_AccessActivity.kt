package com.e_book.AllBookList

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e_book.R
import com.e_book.ThemeActivity
import com.e_book.model.GetBookListResponseItem
import com.e_book.retrofit.ApiRepository

class Extend_AccessActivity : ThemeActivity(), BookExtendAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookExtendAdapter: BookExtendAdapter
    private val booksObserver = MutableLiveData<List<GetBookListResponseItem>>()
    private val errorObserver = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extend_access)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        bookExtendAdapter = BookExtendAdapter(emptyList(), this)
        recyclerView.adapter = bookExtendAdapter

        booksObserver.observe(this, Observer { books ->
            bookExtendAdapter.setBooks(books)
        })

        errorObserver.observe(this, Observer { error ->
            Log.e("Extend_AccessActivity", "Error: $error")
            Toast.makeText(this, "Error fetching books: $error", Toast.LENGTH_SHORT).show()
        })

        fetchBooks()
    }

    private fun fetchBooks() {
        ApiRepository.getBooks(booksObserver, errorObserver)
    }

    override fun onItemClick(book: GetBookListResponseItem) {
        // Handle item click
       /* Toast.makeText(this, "Clicked: ${book.bookName}", Toast.LENGTH_SHORT).show()*/

        // Here you can add more functionality, such as opening a detail view or performing some action based on the clicked item.
    }
}
