package com.e_book.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.e_book.Repository.StickyNoteRepository
import com.e_book.RoomDatabase.AppDatabase

class StickyNoteViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StickyNoteViewModel::class.java)) {
            val repository = StickyNoteRepository(AppDatabase.getDatabase(application).appDao())
            @Suppress("UNCHECKED_CAST")
            return StickyNoteViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
