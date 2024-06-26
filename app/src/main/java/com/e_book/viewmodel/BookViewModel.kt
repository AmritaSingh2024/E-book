package com.e_book.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e_book.model.UserIdBookListResponseItem
import com.e_book.retrofit.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {
    val booksList = MutableLiveData<List<UserIdBookListResponseItem>>()
    val error = MutableLiveData<String>()

    fun fetchUserBooks(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiRepository.getUserBookList(userId)
                Log.d(TAG, "fetchUserBooks: "+response)
                if (response.isSuccessful) {
                    booksList.postValue(response.body())

                } else {
                    error.postValue("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                error.postValue("Server Not Response: ${e.message}")
            }

        }
    }
}
