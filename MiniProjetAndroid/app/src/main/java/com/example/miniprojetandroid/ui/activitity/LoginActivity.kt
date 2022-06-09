package com.example.miniprojetandroid.ui.ui.activitity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.Retrofit.IMyService
import com.example.miniprojetandroid.Retrofit.RetrofitInstance
import com.example.miniprojetandroid.Retrofit.RetrofitInstance.Companion.client
import com.example.miniprojetandroid.models.SignInBody
import com.example.miniprojetandroid.models.User
import com.example.miniprojetandroid.ui.activitity.AuthenticationsActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.util.Assert
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import kotlinx.serialization.*
import okhttp3.ResponseBody
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    lateinit var btnLogin: Button
    lateinit var btnJoin: TextView

    lateinit var txtLayoutLogin: TextInputLayout
    lateinit var txtLayoutPassword: TextInputLayout

    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText
    lateinit var forgotPassword: TextView

    lateinit var compositeDisposable: CompositeDisposable;
    var str: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()


        txtLayoutLogin = findViewById(R.id.txtLayoutEmail)
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword)

        btnLogin = findViewById(R.id.buttonLogin)
        btnJoin = findViewById(R.id.textViewJoinToday)
        forgotPassword = findViewById(R.id.textViewForgotPassword)

        txtEmail = findViewById(R.id.txtEmail)
        txtEmail.setText("yousra.abid@esprit.tn")
        txtPassword = findViewById(R.id.txtPassword)
        txtPassword.setText("123456")

        btnLogin.setOnClickListener {
            Login()
        }

        forgotPassword.setOnClickListener {
            /*val mainIntent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(mainIntent)
            finish()*/
        }

        btnJoin.setOnClickListener {
            val mainIntent = Intent(this, RegisterActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
        // Set error text
        //passwordLayout.error = getString(R.string.error)

        // Clear error text
        //passwordLayout.error = null
    }

    private fun Login() {
        if (validate()) {

            val intent =
                Intent(this@LoginActivity, HomeActivity::class.java)
            intent.putExtra("email", txtEmail.text.toString())
            startActivity(intent)
            finish()
        }
    }

    private fun validate(): Boolean {
        txtLayoutLogin.error = null
        txtLayoutPassword.error = null

        if (txtEmail.text!!.isEmpty()) {
            txtLayoutLogin.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtPassword.text!!.isEmpty()) {
            txtLayoutPassword.error = getString(R.string.mustNotBeEmpty)
            return false
        }

        return true
    }

    private fun signin(email: String, password: String) {
        val retIn = RetrofitInstance.getRetrofitInstance().create(IMyService::class.java)
        val signInInfo = SignInBody(email, password)

        retIn.signin(signInInfo).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.code() == 200) {
                    Toast.makeText(this@LoginActivity, "" + response.message(), Toast.LENGTH_SHORT)
                        .show()
                   /*

                        //val bitmap = (image.drawable as BitmapDrawable).bitmap
                        //val stream = ByteArrayOutputStream()
                        //bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                        //var imageData = stream.toByteArray()
                        //putExtra("image" ,image as ByteArray)
                   */

                } else if (response.code() == 400) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Invalid email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
/*
    fun parse(response: String): User {

        var userC: User = null!!

        val jsonObject = JSONTokener(response).nextValue() as JSONObject

        val jsonArray = jsonObject.getJSONArray("data")

        for (i in 0 until jsonArray.length()) {

            // ID
            val id = jsonArray.getJSONObject(i).getString("id")
            Log.i("id: ", id)

            // User
            val user = jsonArray.getJSONObject(i).getJSONObject("user")

            val username = user.getString("username")
            Log.i("username: ", username)

            val first_name = user.getString("first_name")
            Log.i("first_name: ", first_name)

            val last_name = user.getString("last_name")
            Log.i("last_name: ", last_name)

            val email = user.getString("email")
            Log.i("email: ", email)

            val addresses_p = user.getString("addresses_p")
            Log.i("addresses_p: ", addresses_p)

            val num_tel = user.getString("num_tel")
            Log.i("num_tel: ", num_tel)

            val password = user.getString("password")
            Log.i("password: ", password)


            userC = User(
                id,
                username,
                first_name,
                last_name,
                email,
                addresses_p,
                num_tel,
                password
            )

        }


    }
*/
}
