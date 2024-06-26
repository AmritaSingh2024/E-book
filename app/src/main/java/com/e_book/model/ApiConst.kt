package com.e_book.model

object ApiConst {
    const val BASE_URL = "https://ebook.varcas.org"
    const val LOGIN = "api/Account/Login/Login"
    const val LOGOUT = "api/Account/Logout/Logout"
    const val ACCESS_TOKEN = "AccessToken"
    const val FORGET_PASSWORD = "api/Account/ForgetPassword"
    const val USER_PROFILE="api/Account/GetUserProfile/{id}"
    const val UpdateUser_Profile="api/Account/UpdateUserProfile"
    const val ChangePassword="api/Account/ChangePassword"
const val Notification="api/Other/NotificationReport"
    const val DELETE_Stick="api/Other/DeleteSticky"
const val  AvailableBook="api/UserBook/GetBookList"
    const val BookList="api/UserBook/GetUserBookListByUserId/{id}"
const val FeedBack="api/Other/SaveFeedback"
}
