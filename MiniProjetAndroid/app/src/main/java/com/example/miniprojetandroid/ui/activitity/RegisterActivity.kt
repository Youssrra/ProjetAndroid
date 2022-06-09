package com.example.miniprojetandroid.ui.ui.activitity

import AppLocationService
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.Retrofit.IMyService
import com.example.miniprojetandroid.Retrofit.RetrofitInstance
import com.example.miniprojetandroid.models.emailBody
import com.example.miniprojetandroid.ui.activitity.AuthenticationsActivity
import com.example.miniprojetandroid.utils.GpsTracker
import com.example.miniprojetandroid.utils.LocationAddress
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule


class RegisterActivity : AppCompatActivity() {

    lateinit var btnNext: Button
    lateinit var btnLogin: TextView

    lateinit var txtFirstName: EditText
    lateinit var txtLastName: EditText
    lateinit var txtPhone: EditText
    lateinit var txtEmergencyNumber: EditText
    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText
    lateinit var txtAddressp: EditText
    lateinit var checkBoxLocation: CheckBox
    lateinit var txtUsername: EditText

    lateinit var txtLayoutFirstName : TextInputLayout
    lateinit var txtLayouLastName: TextInputLayout
    lateinit var txtLayoutPhone : TextInputLayout
    lateinit var txtLayoutEmail : TextInputLayout
    lateinit var txtLayoutEmergencyContact : TextInputLayout
    lateinit var txtLayoutPassword : TextInputLayout
    lateinit var txtLayoutAddressp : TextInputLayout
    lateinit var txtLayoutUsername : TextInputLayout

    lateinit var myCode : String

    private var gpsTracker: GpsTracker? = null

    var LONGITUDE : Double = 0.0
    var LATITUDE : Double = 0.0
    lateinit var location: Location
    lateinit var appLocationService: AppLocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        myCode = " "

        btnNext = findViewById(R.id.buttonNext)
        btnLogin = findViewById(R.id.textViewSign_in)

        txtFirstName = findViewById(R.id.txtFirstName)
        txtFirstName.setText("akira")
        txtLayoutFirstName = findViewById(R.id.txtLayoutFirstName)

        txtLastName = findViewById(R.id.txtLastName)
        txtLastName.setText("akira")
        txtLayouLastName = findViewById(R.id.txtLayoutLastName)

        txtPhone = findViewById(R.id.txtPhone)
        txtPhone.setText("55171043")
        txtLayoutPhone = findViewById(R.id.txtLayoutPhone)

        txtEmergencyNumber = findViewById(R.id.txtEmergencyContact)
        txtEmergencyNumber.setText("55171043")
        txtLayoutEmergencyContact = findViewById(R.id.txtLayoutEmergencyContact)

        txtEmail  = findViewById(R.id.txtEmail)
        txtEmail.setText("akiraakira456@gmail.com")
        txtLayoutEmail = findViewById(R.id.txtLayoutEmail)

        txtPassword = findViewById(R.id.txtPassword)
        txtPassword.setText("testyo")
        txtLayoutPassword = findViewById(R.id.txtLayoutPassword)

        txtAddressp =  findViewById(R.id.txtAddressep)
        txtAddressp.setText("Tebourba")
        txtLayoutAddressp = findViewById(R.id.txtLayoutAddressep)

        txtUsername =  findViewById(R.id.txtUsername)
        txtUsername.setText("Akira")
        txtLayoutUsername = findViewById(R.id.txtLayoutUsername)

        checkBoxLocation = findViewById(R.id.checkboxLocation)


        appLocationService = AppLocationService(this)
        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        checkBoxLocation.setOnCheckedChangeListener { buttonView, isChecked ->




            //getLocation()
            Log.d("Latitude"+LATITUDE,"")
            Log.d("Longitude"+LONGITUDE,"")
            getCompleteAddressString(LATITUDE,LONGITUDE)
            //Toast.makeText(this, "Latitude"+LATITUDE+ "Longitude"+LONGITUDE, Toast.LENGTH_SHORT).show()


        }

        checkBoxLocation = findViewById(R.id.checkboxLocation)
        btnNext.setOnClickListener {


            if (validate()){

                sendCode("+216"+txtPhone.text.toString())



                Timer().schedule(5000) {
                    Log.println(Log.INFO, "INFO CODE ", "First : " + myCode)
                    if (myCode != "") {

                        val intent =
                            Intent(this@RegisterActivity, AuthenticationsActivity::class.java)
                        intent.putExtra("username", txtUsername.text.toString())
                        intent.putExtra("firstName", txtFirstName.text.toString())
                        intent.putExtra("lastName", txtLastName.text.toString())
                        intent.putExtra("email", txtEmail.text.toString())
                        intent.putExtra("phone", txtPhone.text.toString())
                        intent.putExtra("emergencyNumber", txtEmergencyNumber.text.toString())
                        intent.putExtra("addressp", txtAddressp.text.toString())
                        intent.putExtra("password", txtPassword.text.toString())
                        intent.putExtra("code", myCode)

                        startActivity(intent)


                    }

                }

            }
        }


        btnLogin.setOnClickListener {
            val mainIntent = Intent(this, LoginActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }

    private fun validate(): Boolean {

        txtLayoutFirstName!!.error = null
        txtLayouLastName!!.error = null
        txtLayoutPhone!!.error = null
        txtLayoutEmail!!.error = null
        txtLayoutEmergencyContact!!.error = null
        txtLayoutPassword!!.error = null
        txtLayoutAddressp!!.error = null
        txtLayoutUsername!!.error = null


        if (txtUsername?.text!!.isEmpty()) {
            txtLayoutUsername!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtUsername?.length()!! < 3) {
            txtLayoutUsername!!.error = getString(R.string.mustBeAtLeast3)
            return false
        }
        if (
            verifyUsername(txtUsername?.text.toString())){
            txtLayoutUsername!!.error = "Username already exist !"
            return false
        }

        if (txtFirstName?.text!!.isEmpty()) {
            txtLayoutFirstName!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtFirstName?.length()!! < 3) {
            txtLayoutFirstName!!.error = getString(R.string.mustBeAtLeast3)
            return false
        }

        if (txtLastName?.text!!.isEmpty()) {
            txtLayouLastName!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtLastName?.length()!! < 3) {
            txtLayouLastName!!.error = getString(R.string.mustBeAtLeast3)
            return false
        }

        if (txtPhone?.text!!.isEmpty()) {
            txtLayoutPhone!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtPhone?.length()!! < 8) {
            txtLayoutPhone!!.error = getString(R.string.mustBeAtLeast8)
            return false
        }
        if (
            verifyPhone(txtPhone?.text.toString())){
            txtLayoutUsername!!.error = "Phone number already exist !"
            return false
        }

        if (txtEmail?.text!!.isEmpty()) {
            txtLayoutEmail!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail?.text!!).matches()) {
            txtLayoutEmail!!.error = getString(R.string.checkYourEmail)
            return false
        }
        if (
            verifyEmail(txtEmail?.text.toString())){
            txtLayoutUsername!!.error = "Email already exist !"
            return false
        }

        if (txtEmergencyNumber?.text!!.isEmpty()) {
            txtLayoutEmergencyContact!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtEmergencyNumber?.length()!! < 8) {
            txtLayoutEmergencyContact!!.error = getString(R.string.mustBeAtLeast8)
            return false
        }

        if (txtPassword?.text!!.isEmpty()) {
            txtLayoutPassword!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtPassword?.length()!! < 6) {
            txtLayoutPassword!!.error = getString(R.string.mustBeAtLeast6)
            return false
        }
        if (txtAddressp?.text!!.isEmpty()) {
            txtLayoutAddressp!!.error = getString(R.string.mustNotBeEmpty)
            return false
        }
        if (txtAddressp?.length()!! < 6) {
            txtLayoutAddressp!!.error = getString(R.string.mustBeAtLeast6)
            return false
        }
        if (txtPassword?.text!!.equals(txtFirstName?.text) or txtPassword?.text!!.equals(txtLastName?.text)) {
            txtLayoutPassword!!.error = getString(R.string.psswordCantBeName)
            return false
        }

        return true
    }

    private fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double) {
        location = appLocationService.getLocation(LocationManager.GPS_PROVIDER)!!
        val latitude = 13.1000727
        val longitude = 80.2126274
        val locationAddress = LocationAddress()
        locationAddress.getAddressFromLocation(
            latitude, longitude, applicationContext, GeoCodeHandler()
        )
        showSettingsAlert()

    }

    private fun showSettingsAlert() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("SETTINGS")
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?")
        alertDialog.setPositiveButton("Settings") { _, _ ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            this@RegisterActivity.startActivity(intent)
        }
        alertDialog.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        alertDialog.show()
    }

    internal inner class GeoCodeHandler : Handler() {
        override fun handleMessage(message: Message) {
            val locationAddress: String
            locationAddress = when (message.what) {
                1 -> ({
                    val bundle = message.data
                    bundle.getString("address")
                }.toString())
                else -> null.toString()
            }

            txtLayoutAddressp!!.error = null

            txtLayoutAddressp!!.error = locationAddress
            Log.d("tttttt "+locationAddress,"")

            //txtAddressp.setText(locationAddress.toString())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ===
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(
                            this@RegisterActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) === PackageManager.PERMISSION_GRANTED)
                    ) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }


    fun getLocation() {
        gpsTracker = GpsTracker(this@RegisterActivity)
        if (gpsTracker!!.canGetLocation()) {
            val latitude: Double = gpsTracker!!.getLatitude()
            val longitude: Double = gpsTracker!!.getLongitude()
            LATITUDE = latitude
             LONGITUDE =longitude
        } else {
            gpsTracker!!.showSettingsAlert()
        }
    }

    private fun verifyUsername(username: String) : Boolean {
        var res : Boolean = false
        val retIn = RetrofitInstance.getRetrofitInstance().create(IMyService::class.java)
        retIn.verifyUsername(username).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                res = false
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200){
                    res = true
                }
            }
        })
        return res
    }

    private fun verifyEmail(email: String) : Boolean {
        var res : Boolean = false
        val retIn = RetrofitInstance.getRetrofitInstance().create(IMyService::class.java)
        retIn.verifyEmail(email).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                res = false
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200){
                    res = true
                }
            }
        })
        return res
    }

    private fun verifyPhone(phone : String) : Boolean {
        var res : Boolean = false
        val retIn = RetrofitInstance.getRetrofitInstance().create(IMyService::class.java)
        retIn.verifyPhone(phone).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                res = false
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200){
                    res = true
                }
            }
        })
        return res
    }

    private fun sendCode(phone: String){
        var code : String = "no"
        var code2 : String = "no"

        val retIn = RetrofitInstance.getRetrofitInstance().create(IMyService::class.java)
        val emailInfo = emailBody(phone)

        retIn.sendCode(emailInfo).enqueue(object : retrofit2.Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@RegisterActivity,"Failed !!",Toast.LENGTH_LONG)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful() ){

                    val stringResponse = response.body()?.string()
                    code = stringResponse!!

                    val n1 = code.takeLast(8)
                    val myc = n1.take(6)
                    myCode = myc.toString()
                    Toast.makeText(this@RegisterActivity,"Success !!",Toast.LENGTH_LONG)
                }
            }


        })

    }
/*
    private fun sendCode(username: String,email : String){
        var code : String = "no"
        var code2 : String = "no"

        val retIn = RetrofitInstance.getRetrofitInstance().create(IMyService::class.java)
        val emailInfo = emailBody(email.toString(),username)

        retIn.sendCode(emailInfo).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
               Toast.makeText(this@RegisterActivity,"Failed !!",Toast.LENGTH_LONG)
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200){
                    val stringResponse = response.body()?.string()
                    code = stringResponse!!

                    val n1 = code.takeLast(8)
                    val myc = n1.take(6)
                    myCode = myc.toString()
                    Toast.makeText(this@RegisterActivity,"Success !!",Toast.LENGTH_LONG)
                }
            }
        })

    }
*/



}





