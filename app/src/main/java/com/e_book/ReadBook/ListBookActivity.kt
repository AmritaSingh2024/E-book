package com.e_book.ReadBook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e_book.R
import com.e_book.RoomDatabase.AppDatabase
import com.e_book.model.UserIdBookListResponseItem
import com.e_book.viewmodel.BookViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListBookActivity : AppCompatActivity(), BookAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private val bookViewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_book)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookAdapter = BookAdapter(emptyList(), this) // Pass context and implement listener
        recyclerView.adapter = bookAdapter

        observeViewModel()
        fetchUserBooks()
    }

    private fun observeViewModel() {
        bookViewModel.booksList.observe(this, Observer { books ->
            books?.let {
                bookAdapter.updateData(it)
            }
        })

        bookViewModel.error.observe(this, Observer { error ->
            error?.let {
                Log.e("ListBookActivity", "Error fetching books: $it")
                // Handle error as needed
            }
        })
    }

    private fun fetchUserBooks() {
        CoroutineScope(Dispatchers.Main).launch {
            val userId = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(this@ListBookActivity).appDao().getUserId()
            }
            bookViewModel.fetchUserBooks(userId)
        }
    }

    // Handle item click from BookAdapter
    override fun onItemClick(book: UserIdBookListResponseItem) {
        val downloadLink = book.downloadLink
        openPdfOrImage(downloadLink)
    }

    // Open PDF or image using WebViewActivity
    private fun openPdfOrImage(downloadLink: String) {
        val intent = Intent(this, WebViewActivity::class.java).apply {
            putExtra("FILE_URL", downloadLink)
        }
        startActivity(intent)
    }
}
