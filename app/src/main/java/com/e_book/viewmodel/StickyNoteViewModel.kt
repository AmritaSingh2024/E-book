package com.e_book.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.e_book.RoomDatabase.StickyNote
import com.e_book.Repository.StickyNoteRepository
import com.e_book.RoomDatabase.AppDatabase
import kotlinx.coroutines.launch

class StickyNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StickyNoteRepository
    val allNotes: LiveData<List<StickyNote>>

    init {
        val stickyNoteDao = AppDatabase.getDatabase(application).appDao()
        repository = StickyNoteRepository(stickyNoteDao)
        allNotes = repository.getAllNotes()
    }

    fun insert(note: StickyNote) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: StickyNote) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(note: StickyNote) = viewModelScope.launch {
        repository.delete(note)
    }
}
