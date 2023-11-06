package com.example.temax

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import com.example.temax.classes.User
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

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

        val request: Request = Request.Builder()
            .url("http://192.168.1.74:3000/users")
            .build()

        client.newCall(request).execute().use {
                response ->
            var res = response.body?.string()
            if(res != null){
                val array = JSONArray(res)
                Log.d("Request",array.toString())

                //converter o array de json para string
                val users = User.importFromJSONArray(array)
                Log.d("Request",users.toString())
            }
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
        }
    }

    //função para retornar os valores para o activity anterior
    fun end(){
        val returnIntent = Intent()
        // não esquecer de por sempre o to string porque senão vai retornal o editavel em vez de string
        //TODO: por aqui a variavel para passar para o login
        //returnIntent.putExtra("email", xxxxxx)
        setResult(Activity.RESULT_OK, returnIntent)
    }

    fun verificationsRegister(email: String, password: String, dateNasc: String ): Boolean{
        return true
    }
}