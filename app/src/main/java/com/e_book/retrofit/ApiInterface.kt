package com.e_book.retrofit

import com.e_book.model.*
import com.e_book.model.ApiConst.ChangePassword
import com.e_book.model.ApiConst.DELETE_Stick
import com.e_book.model.ApiConst.FeedBack
import com.e_book.model.ApiConst.LOGIN
import com.e_book.model.ApiConst.LOGOUT
import com.e_book.model.ApiConst.Notification

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @POST(LOGIN)
    suspend fun login(@Body jsonObject: JsonObject): Response<LoginDataResponse>
    @POST(ChangePassword)
    suspend fun changePassword(@Body changepasswordRequest: ChangepasswordRequest): Response<ChangePasswordResponse>

    @POST("api/Account/SaveRegistraion")
    suspend fun  registerUser(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse>
    @POST(FeedBack)
    suspend fun submitFeedback(@Body feedbackRequest: FeedbackRequest): Response<ResponseFeedback>
    @POST(LOGOUT)
    suspend fun logout(): Response<String>

    @GET("api/UserBook/GetUserBookListByUserId/{id}")
    suspend fun getUserBookList(@Path("id") id: Int): Response<List<UserIdBookListResponseItem>>
@POST("api/AppVersion/SaveAppVersion")
fun saveAppVersion(@Body appVersionRequest: AppVersionRequest): Response<AppVersionresponse>

    @GET("api/Account/GetUserProfile/{id}")
    suspend fun getUserProfile(@Path("id") id: Int): Response<GetProfileResponse>
    @POST("api/Account/UpdateUserProfile")
    suspend fun updateUserProfile(@Body updatedProfile: UpdateUserProfileRequest): Response<UpdateUserProfileResponse>


    @GET(Notification)
    suspend fun getNotifications(): Response<List<NotificationResponseItem>>
    @GET("api/Other/CouponReport")
    suspend fun getCoupon():Response<List<CouponResponsItem>>

    @GET("api/Other/Sticky")
    fun getStickyNotes(@Query("Id") id: Int): Call<List<StickyNoteGet>>

    @POST(DELETE_Stick)
    suspend fun deleteStick(@Query("id") id: Int): Response<String>
    @GET("api/UserBook/GetBookList")
    fun getBooks(): Call<GetBookListResponse>
    @GET("api/UserBook/GetBookMaterialList")
    suspend fun  getMaterialBooklist():Response<List<GetMaterialResponseItem>>
@GET("api/UserBook/GetBookByBookId")
suspend fun getbookbyBookId():Response<GetBookByBookId>

}