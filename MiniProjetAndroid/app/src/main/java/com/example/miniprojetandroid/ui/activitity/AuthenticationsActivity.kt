package com.example.miniprojetandroid.ui.activitity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.Retrofit.IMyService
import com.example.miniprojetandroid.Retrofit.RetrofitInstance
import com.example.miniprojetandroid.models.UserBody
import com.example.miniprojetandroid.ui.ui.activitity.LoginActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


class AuthenticationsActivity : AppCompatActivity() {

    lateinit var btnVerify: Button

    lateinit var codeDigit1: EditText
    lateinit var codeDigit2: EditText
    lateinit var codeDigit3: EditText
    lateinit var codeDigit4: EditText
    lateinit var codeDigit5: EditText
    lateinit var codeDigit6: EditText

    lateinit var txtResentCode : TextView
    lateinit var txtviewPhone: TextView
    lateinit var errorcode : TextView

    lateinit var code : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)


        btnVerify = findViewById(R.id.btnverify)
        txtResentCode = findViewById(R.id.btnResendCode)

        codeDigit1 = findViewById(R.id.codeDigit1)
        codeDigit2 = findViewById(R.id.codeDigit2)
        codeDigit3 = findViewById(R.id.codeDigit3)
        codeDigit4 = findViewById(R.id.codeDigit4)
        codeDigit5  = findViewById(R.id.codeDigit5)
        codeDigit6 = findViewById(R.id.codeDigit6)

        txtviewPhone =  findViewById(R.id.txtviewPhone)
        errorcode =  findViewById(R.id.errorcode)


        val profileName = intent.getStringExtra("username")
        Log.println(Log.INFO,"INFO CODE ", "Your message to print : "+profileName)

        Log.println(Log.INFO,"INFO CODE ", "Your message to print : "+intent.getStringExtra("code"))


        code  = intent.getStringExtra("code").toString()

        Log.println(Log.INFO,"INFO CODE ", "Your message to print fin : "+code)

        codeDigit1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable != null) {
                    if (editable.length == 1) codeDigit2.requestFocus()
                }
            }
        })
        codeDigit2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable != null) {
                    if (editable.length == 1) codeDigit3.requestFocus()
                }
            }
        })
        codeDigit3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable != null) {
                    if (editable.length == 1) codeDigit4.requestFocus()
                }
            }
        })
        codeDigit4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable != null) {
                    if (editable.length == 1) codeDigit5.requestFocus()
                }
            }
        })
        codeDigit5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable != null) {
                    if (editable.length == 1) codeDigit6.requestFocus()
                }
            }
        })

        txtResentCode.setOnClickListener {
            //val newCode = RegisterActivity().sendCode( bundle.get("username").toString(),bundle.get("email").toString())
            //code = newCode
           // Log.println(Log.INFO,"INFO CODE ", "new code 1 : "+newCode)
           // Log.println(Log.INFO,"INFO CODE ", "new code : "+code)
            codeDigit1.text.clear()
            codeDigit2.text.clear()
            codeDigit3.text.clear()
            codeDigit4.text.clear()
            codeDigit5.text.clear()
            codeDigit6.text.clear()
        }

        btnVerify.setOnClickListener {
            val inpCode : String = codeDigit1.text.toString()+
                    codeDigit2.text.toString()+
                    codeDigit3.text.toString()+
                    codeDigit4.text.toString()+
                    codeDigit5.text.toString()+
                    codeDigit6.text.toString()

            Log.println(Log.INFO,"INFO CODE ", "Your message to print : "+inpCode)
            Log.println(Log.INFO,"INFO CODE ", "Your message to print : "+code)

            if(inpCode == code){
                signup(
                    intent.getStringExtra("username").toString(),
                    intent.getStringExtra("firstName").toString(),
                    intent.getStringExtra("lastName").toString(),
                    intent.getStringExtra("email").toString(),
                    intent.getStringExtra("phone").toString(),
                    intent.getStringExtra("emergencyNumber").toString(),
                    intent.getStringExtra("addressp").toString(),
                    intent.getStringExtra("password").toString()
                )

                val intent = Intent(this, PopUpWindow::class.java)
                intent.putExtra("popuptext", "Registration completed successfully !")
                intent.putExtra("popupbtn", "OK")
                intent.putExtra("darkstatusbar", false)
                startActivity(intent)

            }
            else {
                Toast.makeText(this@AuthenticationsActivity,"Invalid code please try again !!",Toast.LENGTH_LONG)
            }
        }





        }

    private fun signup(username: String,first_name: String, last_name: String, email: String,
                       phone: String, contact: String,addresses_p: String,password: String) {
        val retIn = RetrofitInstance.getRetrofitInstance().create(IMyService::class.java)
        val registerInfo = UserBody(username,first_name ,last_name,email,phone,contact,addresses_p,password)

        retIn.registerUser(registerInfo).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@AuthenticationsActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 201) {
                    Toast.makeText(this@AuthenticationsActivity, "Registration success!", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@AuthenticationsActivity, "Registration failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

}
