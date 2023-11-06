package com.example.temax

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    val emailEditText: EditText by lazy { findViewById(R.id.main_editText) }
    val passwordEditText: EditText by lazy { findViewById(R.id.main_editText2)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = intent.getStringExtra("EMAIL")

        emailEditText.setText(email)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if the result is OK and handle the received data
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Now you can use resultData as needed
            val emailReceived = data?.getStringExtra("email")

            if (emailReceived != null) {
                //usado para meter o texto na caixa de email se não for null
                runOnUiThread {
                    emailEditText.setText(emailReceived)
                }
            }
        }
    }

    fun main_buttonLogin(view: View) {

        val r = Runnable {
            validate(emailEditText.text.toString(),passwordEditText.text.toString())
        }
        val t = Thread(r)
        t.start()

    }

    fun main_buttonRegister(view: View){
        val intent = Intent(this@MainActivity, Register::class.java)
        startActivityForResult(intent,1)
    }

    fun validate(emailAdrees: String, password: String){
        //TODO: fazer aqui depois o request a API para ver se tem permissao
        val client = OkHttpClient()


        var jsonBody = """
{
    "Email": "${emailEditText.text}",
    "Password": "${passwordEditText.text}"
}
"""

        // Define the request body and media type (usually JSON)
        val requestBody =
            jsonBody.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val  requestBuilder = Request.Builder()
            .url("http://${BuildConfig.API_IP}:3000/users/login")
            .post(requestBody)

        //headers que preciso para o Post
        val headers = mapOf(
            "Content-Type" to "application/json"
        )

        // Add headers if needed
        headers?.forEach { (key, value) ->
            requestBuilder.addHeader(key, value)
        }

        val request = requestBuilder.build()

        try {
            val response: Response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful) {
                // Handle the successful response
                // Here, you can check the response body or perform any validation
                val isValid = validateResponse(responseBody)
                if(isValid){
                    //se for o login existir passa para a activyti seguinte
                    val intent = Intent(this@MainActivity, SelectTypeUser::class.java)
                    startActivityForResult(intent,1)
                }
            } else {
                // Handle the error response
                Log.d("ErrorPost","deu merda no post");
                // You can log the error or return false, indicating validation failure
            }
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle network or other exceptions
        }

    }
}

fun validateResponse(responseBody: String?): Boolean {
    // Implement your response validation logic here
    // Parse the response and check if it's valid
    // For example, check if the response contains "Login successful" or other relevant data

    // Replace the following line with your actual validation logic
    return responseBody?.contains("Login exists") == true
}