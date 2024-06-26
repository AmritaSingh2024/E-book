package com.e_book.MaterialList

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e_book.R
import com.e_book.ThemeActivity
import com.e_book.model.GetMaterialResponseItem
import com.e_book.retrofit.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MaterialActivity : ThemeActivity(), MaterialAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookMaterialAdapter: MaterialAdapter
    private val booksObserver = MutableLiveData<List<GetMaterialResponseItem>>()
    private val errorObserver = MutableLiveData<String>()
    private val apiRepository: ApiRepository by lazy { ApiRepository }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        bookMaterialAdapter = MaterialAdapter(emptyList(), this)
        recyclerView.adapter = bookMaterialAdapter

        booksObserver.observe(this, Observer { books ->
            bookMaterialAdapter.setBooks(books)
        })

        errorObserver.observe(this, Observer { error ->
            Log.e("MaterialActivity", "Error: $error")
            Toast.makeText(this, "Error fetching books: $error", Toast.LENGTH_SHORT).show()
        })

        fetchBooks()
    }

    private fun fetchBooks() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val booksResponse = apiRepository.getMaterialBooklist(booksObserver, errorObserver)
                // Handle response as needed
            } catch (e: Exception) {
                // Handle exceptions
                Log.e("fetchBooks", "Error: ${e.message}", e)
                errorObserver.postValue("Error: ${e.message}")
            }
        }
    }
    override fun onItemClick(book: GetMaterialResponseItem) {
        // Handle item click
        Toast.makeText(this, "Clicked: ${book.bookName}", Toast.LENGTH_SHORT).show()

        // Here you can add more functionality, such as opening a detail view or performing some action based on the clicked item.
    }
}
