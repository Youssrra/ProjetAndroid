package com.example.miniprojetandroid.Retrofit


import com.example.miniprojetandroid.models.SignInBody
import com.example.miniprojetandroid.models.User
import com.example.miniprojetandroid.models.UserBody
import com.example.miniprojetandroid.models.emailBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


public interface IMyService {


    @Headers("Content-Type:application/json")
    @POST("api/auth/signin")
    fun signin(@Body info: SignInBody): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("/api/send")
    fun sendCode(@Body info: emailBody): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @GET("api/verify/email/{email}")
    fun verifyEmail(@Path("email") email : String): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("api/verify/username/{username}")
    fun verifyUsername(@Path("username") username: String?): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @GET("api/verify/phone/{phone}")
    fun verifyPhone(@Path("phone") phone: String?): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("api/auth/signup")
    fun registerUser(
        @Body info: UserBody
    ): retrofit2.Call<ResponseBody>

}
class RetrofitInstance {
    companion object {
        val BASE_URL: String = "http://192.168.1.108:3000/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        }
    }

}

/*

    @POST("api/auth/signin")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Observable<BaseResponse<User?>?>?


    @POST("register")
    @FormUrlEncoded
    fun registerUser(
    @Field("email") email: String?,
    @Field("first_name") first_name: String?,
    @Field("last_name") last_name: String?,
    @Field("username")  username: String?,
    @Field("phone")  phone: String?,
    @Field("password")  password: String?,
    @Field("emergency_contact")  emergency_contact: String?
    ):Observable<String>

    @POST("api/auth/signin")
    @FormUrlEncoded
    fun logiUser(
    @Field("email") email : String?,
    @Field("password") password: String? ):Observable<String>
    }
*/
