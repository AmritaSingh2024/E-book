package com.e_book.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e_book.model.ChangePasswordResponse
import com.e_book.model.ChangepasswordRequest
import com.e_book.retrofit.ApiRepository

class ChangePasswordViewModel : ViewModel() {
    val changePasswordResponse: MutableLiveData<ChangePasswordResponse> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun changePassword(request: ChangepasswordRequest) {
        ApiRepository.changePasswordAPI(request, changePasswordResponse, error)
    }
}
