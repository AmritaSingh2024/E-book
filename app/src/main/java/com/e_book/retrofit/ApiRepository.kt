package com.e_book.retrofit
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.e_book.RoomDatabase.AppDatabase
import com.e_book.model.*
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

object ApiRepository {
    private val apiService = ApiClient.create()

    suspend fun logout(): Response<String> {
        return withContext(Dispatchers.IO) {
            apiService.logout()
        }
    }
    suspend fun getCoupons(): Response<List<CouponResponsItem>> {
        return apiService.getCoupon()
    }

    fun loginAPI(
        context: Context,
        jsonObject: JsonObject,
        allApplicationObserver: MutableLiveData<LoginDataResponse>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("loginAPI", "loginAPI:$jsonObject")
            try {

                val sendLoginData = apiService.login(jsonObject)
                Log.d("loginAPI", "loginAPI: $sendLoginData")
                if (sendLoginData.isSuccessful && sendLoginData.code() == 200) {
                    allApplicationObserver.postValue(sendLoginData.body())
                    Log.d("login response ", sendLoginData.body().toString())
                    AppDatabase.getDatabase(context).appDao()
                        .insertLoginResponse(convertApiResponseToEntity(sendLoginData.body()!!))
                    val list = AppDatabase.getDatabase(context).appDao().getLoginData()
                    if (list.size > 0) {
                        Log.e("logiStoredData", " ${list.size}")
                    } else {
                        Log.e("logiStoredData", " ${list.size}")
                    }
                } else {
                    errorObserver.postValue("An error occurred: ${sendLoginData.code()}")
                }
            } catch (exception: Exception) {
                Log.e("loginAPI", "API call failed: ${exception.message}", exception)
                errorObserver.postValue("Server Not Response: ${exception.message}")
            }
        }
    }

    fun changePasswordAPI(
        changepasswordRequest: ChangepasswordRequest,
        responseObserver: MutableLiveData<ChangePasswordResponse>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.changePassword(changepasswordRequest)
                if (response.isSuccessful) {
                    responseObserver.postValue(response.body())
                } else {
                    errorObserver.postValue("Error: ${response.code()}")
                }
            } catch (exception: Exception) {
                Log.e("changePasswordAPI", "API call failed: ${exception.message}", exception)
                errorObserver.postValue("Server Not Response: ${exception.message}")
            }
        }
    }

    suspend fun getNotifications(): Response<List<NotificationResponseItem>> {
        return apiService.getNotifications()
    }

    fun convertApiResponseToEntity(apiResponse: LoginDataResponse): LoginDataResponse {
        return LoginDataResponse(
            id = apiResponse.id,
            token = apiResponse.token,
            userId = apiResponse.userId,
            userName = apiResponse.userName
        )
    }

    /*fun getUserProfile(userId: String): JsonObject {
        val jsonObject = JsonObject()
        try {
            jsonObject.addProperty("userId", userId)
        } catch (e: Exception) {
            e.message
        }

        return jsonObject
    }
*/
    fun getUserProfile(
        userId: Int,
        allApplicationObserver: MutableLiveData<GetProfileResponse>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<GetProfileResponse> = apiService.getUserProfile(userId)
                Log.d("getUserProfile", "Response: $response")

                if (response.isSuccessful) {
                    val profileData = response.body()
                    withContext(Dispatchers.Main) {
                        if (profileData != null) {
                            allApplicationObserver.value = profileData
                        } else {
                            errorObserver.value = "Profile data is null"
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        errorObserver.value = "Error: ${response.code()}"
                    }
                }
            } catch (exception: Exception) {
                Log.e("getUserProfile", "API call failed: ${exception.message}", exception)
                withContext(Dispatchers.Main) {
                    errorObserver.value = "Server Not Response: ${exception.message}"
                }
            }
        }
    }
    suspend fun updateUserProfile(
        updatedProfile: UpdateUserProfileRequest,
        successObserver: MutableLiveData<UpdateUserProfileResponse>,
        errorObserver: MutableLiveData<String>
    ) {
        try {
            val response = apiService.updateUserProfile(updatedProfile)
            if (response.isSuccessful) {
                val updatedData = response.body()
                withContext(Dispatchers.Main) {
                    if (updatedData != null) {
                        successObserver.value = updatedData
                    } else {
                        errorObserver.value = "Updated data is null"
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    errorObserver.value = "Error: ${response.code()}"
                }
            }
        } catch (exception: Exception) {
            Log.e("updateUserProfile", "API call failed: ${exception.message}", exception)
            withContext(Dispatchers.Main) {
                errorObserver.value = "Server Not Response: ${exception.message}"
            }
        }
    }

    fun getBooks(
        booksObserver: MutableLiveData<List<GetBookListResponseItem>>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getBooks().execute()
                if (response.isSuccessful) {
                    val booksList = response.body()
                    if (booksList != null) {
                        booksObserver.postValue(booksList)
                    } else {
                        errorObserver.postValue("No books found")
                    }
                } else {
                    errorObserver.postValue("Error: ${response.code()}")
                }
            } catch (exception: Exception) {
                Log.e("getBooks", "API call failed: ${exception.message}", exception)
                errorObserver.postValue("Server Not Response: ${exception.message}")
            }
        }
    }

    suspend fun getMaterialBooklist(
        booksObserver: MutableLiveData<List<GetMaterialResponseItem>>,
        errorObserver: MutableLiveData<String>
    ) {
        try {
            val response = apiService.getMaterialBooklist()
            if (response.isSuccessful) {
                val booksList = response.body()
                withContext(Dispatchers.Main) {
                    if (booksList != null) {
                        booksObserver.value = booksList
                    } else {
                        errorObserver.value = "No books found"
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    errorObserver.value = "Error: ${response.code()}"
                }
            }
        } catch (exception: Exception) {
            Log.e("getMaterialBooklist", "API call failed: ${exception.message}", exception)
            withContext(Dispatchers.Main) {
                errorObserver.value = "Server Not Response: ${exception.message}"
            }
        }
    }


    suspend fun getUserBookList(userId: Int): Response<List<UserIdBookListResponseItem>> {
        return apiService.getUserBookList(userId)
    }
    suspend fun registerUser(
        registrationRequest: RegistrationRequest,
        successObserver: MutableLiveData<RegistrationResponse>,
        errorObserver: MutableLiveData<String>
    ) {
        Log.d("ApiRepository", "registerUser called with request: $registrationRequest")
        try {
            val response = apiService.registerUser(registrationRequest)
            Log.d("ApiRepository", "API response: $response")
            if (response.isSuccessful) {
                val registrationResponse = response.body()
                withContext(Dispatchers.Main) {
                    if (registrationResponse != null) {
                        Log.d("ApiRepository", "Registration successful: $registrationResponse")
                        successObserver.value = registrationResponse
                    } else {
                        Log.e("ApiRepository", "Registration response is null")
                        errorObserver.value = "Registration response is null"
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Log.e("ApiRepository", "API error: ${response.code()}")
                    errorObserver.value = "Error: ${response.code()}"
                }
            }
        } catch (exception: Exception) {
            Log.e("ApiRepository", "API call failed: ${exception.message}", exception)
            withContext(Dispatchers.Main) {
                errorObserver.value = "Server Not Response: ${exception.message}"
            }
        }
    }
    suspend fun submitFeedback(feedbackRequest: FeedbackRequest): Response<ResponseFeedback> {
        return withContext(Dispatchers.IO) {
            apiService.submitFeedback(feedbackRequest)
        }
    }


}