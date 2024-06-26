package com.e_book.Repository
import androidx.lifecycle.LiveData
import com.e_book.RoomDatabase.AppDao
import com.e_book.RoomDatabase.StickyNote

class StickyNoteRepository(private val appDao: AppDao) {

    suspend fun insert(stickyNote: StickyNote) {
        appDao.insert(stickyNote)
    }

    suspend fun update(stickyNote: StickyNote) {
        appDao.update(stickyNote)
    }

    suspend fun delete(stickyNote: StickyNote) {
        appDao.delete(stickyNote)
    }

    fun getAllNotes(): LiveData<List<StickyNote>> {
        return appDao.getAllNotes()
    }
    suspend fun deleteAll() {
        appDao.deleteAll()
    }
}
