package com.e_book.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_book.model.GetProfileResponse
import com.e_book.model.UpdateUserProfileRequest
import com.e_book.model.UpdateUserProfileResponse
import com.e_book.retrofit.ApiRepository
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel() {

    private val repository = ApiRepository

    private val _profileLiveData = MutableLiveData<GetProfileResponse>()
    val profileLiveData: LiveData<GetProfileResponse>
        get() = _profileLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData
    private val _updateSuccessLiveData = MutableLiveData<UpdateUserProfileResponse>()
    val updateSuccessLiveData: LiveData<UpdateUserProfileResponse>
        get() = _updateSuccessLiveData

    fun getUserProfile(userId: Int) {
        viewModelScope.launch {
            repository.getUserProfile(userId, _profileLiveData, _errorLiveData)
        }
    }fun updateUserProfile(updatedProfile: UpdateUserProfileRequest) {
        viewModelScope.launch {
            repository.updateUserProfile(updatedProfile, _updateSuccessLiveData, _errorLiveData)
        }
    }
}

