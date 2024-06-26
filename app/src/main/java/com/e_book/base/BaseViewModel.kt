package com.e_book.base

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel<T>(application: Application) : AndroidViewModel(application) {
    var isLoading = ObservableField(false)

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }
}