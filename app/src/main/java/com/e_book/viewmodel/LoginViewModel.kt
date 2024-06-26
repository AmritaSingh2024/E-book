package com.e_book.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.e_book.model.LoginDataResponse
import com.e_book.retrofit.ApiRepository
import com.google.gson.JsonObject

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val allApplicationObserver = MutableLiveData<LoginDataResponse>()
    val errorObserver = MutableLiveData<String>()

    fun getLogin(context: Context, jsonObject: JsonObject) {
        // Call the API repository to initiate login
        ApiRepository.loginAPI(context, jsonObject, allApplicationObserver, errorObserver)
    }
}
