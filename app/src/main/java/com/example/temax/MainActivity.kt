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

        // Cria um cliente OkHttpClient para fazer a solicitação HTTP
        val client = OkHttpClient()

        // Cria um JSON body contendo o email e a password do user
        var jsonBody = """
      {
        "Email": "${emailEditText.text}",
        "Password": "${passwordEditText.text}"
      }
        """

        // Define o conteudo do corpo e cria esse corpo em JSON
        val requestBody = jsonBody.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        // Cria um objeto RequestBuilder para construir a solicitação HTTP
        val  requestBuilder = Request.Builder()
            .url("http://${BuildConfig.API_IP}:3000/users/login")
            .post(requestBody)

        //cabeçalhos que preciso para o Post
        val headers = mapOf(
            "Content-Type" to "application/json"
        )

        // Adiciona os cabeçalhos da solicitação
        headers?.forEach { (key, value) ->
            requestBuilder.addHeader(key, value)
        }
        // Constroi a solicitação final, com o corpo e respectivo cabeçalho
        val request = requestBuilder.build()

        try {
            // Executa a solicitação HTTP
            val response: Response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful) {
                // Se a resposta for bem-sucedida passa para
                val isValid = verificationsLogin(responseBody)
                if(isValid){

                    //se os parametros do login estiverem corretos passa para a activity SelectTyoeYser
                    val intent = Intent(this@MainActivity, SelectTypeUser::class.java)
                    startActivityForResult(intent,1)

                    // Exibe a mensagem de sucesso
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, getString(R.string.login_sucess), Toast.LENGTH_SHORT).show()
                    }

                }else {
                   runOnUiThread{
                       // Exibe a mensagem de erro
                       Toast.makeText(this@MainActivity, getString(R.string.login_failure), Toast.LENGTH_SHORT).show()
                   }

                    // Caso esteja errado, define-se aqui as mensagens de erro para ver no LogCat
                    Log.d("ErrorPost","Post nao funcionou")
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
            runOnUiThread {
                // Exibe uma mensagem de erro de rede
                Toast.makeText(this@MainActivity, getString(R.string.network_error_message), Toast.LENGTH_SHORT).show()
            }
        }

    }
}

fun verificationsLogin(responseBody: String?): Boolean {

    return responseBody?.contains("Login exists") == true
}