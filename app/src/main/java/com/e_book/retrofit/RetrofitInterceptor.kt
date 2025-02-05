package com.example.loginapidemo.retrofit

import com.e_book.model.ApiConst.ACCESS_TOKEN
import okhttp3.Interceptor

object RetrofitInterceptor {


    fun headersInterceptor() = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader(
                    "Authorization",""

/*
                    "Bearer ${sharedPreferenceClass.getString(ACCESS_TOKEN)}"*/
                )
                .build()

        )
    }
}