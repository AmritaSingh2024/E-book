package com.e_book.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e_book.model.FeedbackRequest
import com.e_book.model.ResponseFeedback
import com.e_book.retrofit.ApiRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class FeedbackViewModel : ViewModel() {

    val feedbackResponse: MutableLiveData<Response<ResponseFeedback>> = MutableLiveData()
    val feedbackError: MutableLiveData<String> = MutableLiveData()

    fun submitFeedback(userId: Int, title: String, message: String) {

        val feedbackRequest = FeedbackRequest(userId, title, message,)
        viewModelScope.launch {
            try {
                val response = ApiRepository.submitFeedback(feedbackRequest)
                if (response.isSuccessful) {
                    feedbackResponse.postValue(response)
                } else {
                    feedbackError.postValue("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                feedbackError.postValue("Server Not Response: ${e.message}")
            }
        }
    }
}
