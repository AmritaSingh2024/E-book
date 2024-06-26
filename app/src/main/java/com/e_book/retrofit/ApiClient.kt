package com.e_book.retrofit

import com.example.loginapidemo.retrofit.RetrofitInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private const val BASE_URL = "https://ebook.varcas.org"
        fun create(): ApiInterface {
           val connectTimeOutInsec: Long = NetworkConstants.DEFAULT_CONNECTION_TIMEOUT_SEC
           val readTimeoutSec: Long = NetworkConstants.DEFAULT_READ_TIME_OUT
           val writeTimeoutSec: Long = NetworkConstants.DEFAULT_WRITE_TIME_OUT
            val client = OkHttpClient.Builder()
            /*client.hostnameVerifier(HostnameVerifier { hostname, session -> true })*/
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(loggingInterceptor)
            client.addInterceptor(RetrofitInterceptor.headersInterceptor())
            client.connectTimeout(connectTimeOutInsec, TimeUnit.SECONDS)
            client.readTimeout(readTimeoutSec, TimeUnit.SECONDS)
            client.writeTimeout(writeTimeoutSec, TimeUnit.SECONDS)
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}
