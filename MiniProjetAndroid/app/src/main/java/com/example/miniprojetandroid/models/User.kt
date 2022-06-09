package com.example.miniprojetandroid.models

import com.google.gson.annotations.SerializedName

data class User (
    var id: String,
    var username: String,
    val first_name : String,
    var last_name :String,
    var email: String,
    var addresses_p : String,
    var num_tel : String,
    var password: String
   // var accessToken : String

)
class UsersList {
    @SerializedName("items")
    var users: List<User>? = null
}
data class SignInBody(val email: String, val password: String)

//data class emailBody(val email: String, val username: String)

data class emailBody(val phone: String)

data class UserBody(
    val username : String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val num_tel : String,
    val contact : String,
    val addresses_p: String,
    val password: String)