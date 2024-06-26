package com.e_book.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_book.retrofit.ApiRepository
import kotlinx.coroutines.launch

class LogoutViewModel : ViewModel() {
    val logoutResponseLiveData = MutableLiveData<Unit>()
    val errorLiveData = MutableLiveData<String>()

    fun logout() {
        viewModelScope.launch {
            try {
                val response = ApiRepository.logout()
                if (response.isSuccessful) {
                    logoutResponseLiveData.postValue(Unit)
                } else {
                    errorLiveData.postValue("Logout failed: ${response.code()}")
                }
            } catch (e: Exception) {
                errorLiveData.postValue("Response Not Server: ${e.message}")
            }
        }
    }
}
