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

class Register : AppCompatActivity() {

    val NameEditText: EditText by lazy { findViewById(R.id.register_NameText) }
    val emailEditText: EditText by lazy { findViewById(R.id.register_EmailText) }
    val passwordEditText: EditText by lazy { findViewById(R.id.register_passwordText)}
    val dateEditText: EditText by lazy { findViewById(R.id.register_birthDate)}
    val contactEditText: EditText by lazy { findViewById(R.id.register_contactNumber)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    //todo: por dentro deste request o body para enviar o user
    fun secondary_request() {
        val client = OkHttpClient()


        var jsonBody = """
{
    "Name": "${NameEditText.text}",
    "Email": "${emailEditText.text}",
    "Password": "${passwordEditText.text}",
    "Date_birth": "${dateEditText.text}",
    "Contact": ${contactEditText.text}
}
"""
    Log.d("debug",jsonBody)
        // Define the request body and media type (usually JSON)
        val requestBody =
            jsonBody.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val  requestBuilder = Request.Builder()
            .url("http://192.168.1.74:3000/users/createUser")
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
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("Error",e.toString())

        }
    }
    fun registar_button(view: View){
        if(verificationsRegister(emailEditText.text.toString(),
                passwordEditText.text.toString(),
                dateEditText.text.toString())) {

            /// codigo abaixo envia o request para o background
            val r = Runnable {
                //todo:passar como parametro os dados para meter no body
                secondary_request()
            }
            val t = Thread(r)
            t.start()

            Toast.makeText(this,"boa criaste conta",Toast.LENGTH_SHORT).show()



        }
    }

    //função para retornar os valores para o activity anterior
    fun end(){
        val returnIntent = Intent()
        // não esquecer de por sempre o to string porque senão vai retornal o editavel em vez de string
        //TODO: por aqui a variavel para passar para o login
        returnIntent.putExtra("email", emailEditText.text)
        setResult(Activity.RESULT_OK, returnIntent)
    }

    fun verificationsRegister(email: String, password: String, dateNasc: String ): Boolean{
        return true
    }
}