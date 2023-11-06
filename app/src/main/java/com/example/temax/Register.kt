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
            .url("http://${BuildConfig.API_IP}:3000/users/createUser")
            .post(requestBody)

        //headers que preciso para o Post
        val headers = mapOf(
            "Content-Type" to "application/json"
        )

        // Add headers if needed
        headers?.forEach { (key, value) ->
            requestBuilder.addHeader(key, value)
        }

    }
    fun registar_button(view: View){



        if (verificationsRegister(
                emailEditText.text.toString(),
                passwordEditText.text.toString(),
                dateEditText.text.toString(),
                contactEditText.text.toString(),
                NameEditText.text.toString()
            )) {

            /// codigo abaixo envia o request para o background
            val r = Runnable {
                //todo:passar como parametro os dados para meter no body
                secondary_request()
            }
            val t = Thread(r)
            t.start()

            //Mensagem de Conta criada
            Toast.makeText(this@Register, getString(R.string.account_created),Toast.LENGTH_SHORT).show()

            end()
            finish()


        }
    }

    //função para retornar os valores para o activity anterior
    fun end(){
        val returnIntent = Intent()
        // não esquecer de por sempre o to string porque senão vai retornal o editavel em vez de string
        returnIntent.putExtra("email", emailEditText.text.toString())
        setResult(Activity.RESULT_OK, returnIntent)
    }

    fun verificationsRegister(email: String, password: String, dateNasc: String, contact: String, name: String): Boolean{

        var isValid = true

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            // Verifica se o email está vazio ou se não é um endereço de email válido.
            Toast.makeText(this@Register, getString(R.string.email_error_message) , Toast.LENGTH_SHORT).show()
            isValid = false
        }

        val passwordPattern = """^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@#$%^&+=]).*$""".toRegex()
        if (password.isEmpty() || password.length < 6 || !passwordPattern.matches(password)) {

            // Verifica se a senha está vazia ou
            Toast.makeText(this@Register, getString(R.string.Pasword_error_message), Toast.LENGTH_SHORT).show()
            isValid = false
        }

        val datePattern = """^\d{4}-\d{2}-\d{2}$""".toRegex()
        if (dateNasc.isEmpty() || !datePattern.matches(dateNasc)) {

            Toast.makeText(this@Register, getString(R.string.Date_error_message) , Toast.LENGTH_SHORT).show()
            isValid = false
        }

        val contactInt = contact.toIntOrNull()
        if (contactInt == null || contact.length != 9) {

            Toast.makeText(this@Register, getString(R.string.Contact_error_message) , Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (name.isEmpty()) {

            Toast.makeText(this@Register, getString(R.string.Name_error_message), Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Retorna o valor acumulado de isValid
        return isValid

    }
}