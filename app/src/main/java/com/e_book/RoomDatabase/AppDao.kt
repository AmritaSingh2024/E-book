package com.e_book.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.e_book.model.LoginDataResponse

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(registerModel: RegisterModel)

    @Insert
    suspend fun insertLoginResponse(loginDataResponse: LoginDataResponse)

    @Query("SELECT * FROM Login")
   suspend fun getLoginData() : List<LoginDataResponse>

   @Query("Delete from Login")
   suspend fun deletAll()

    @Query("SELECT userId FROM Login")
    fun getUserId(): Int

    @Update
    suspend fun update(registerModel: RegisterModel)

    @Delete
    suspend fun delete(registerModel: RegisterModel)

    @Query("DELETE FROM user_registration")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_registration ORDER BY id ASC")
    fun getAllRegistrations(): LiveData<List<RegisterModel>>

    @Insert
    suspend fun insert(note: StickyNote)

    @Update
    suspend fun update(note: StickyNote)

    @Delete
    suspend fun delete(note: StickyNote)

    @Query("SELECT * FROM sticky_notes ORDER BY date DESC")
    fun getAllNotes(): LiveData<List<StickyNote>>
}
