package com.example.temax

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText

class Register : AppCompatActivity() {

    val emailEditText: EditText by lazy { findViewById(R.id.editTextText) }
    val passwordEditText: EditText by lazy { findViewById(R.id.editTextTextPassword)}
    val dateEditText: EditText by lazy { findViewById(R.id.editTextDate)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun registar_button(view: View){
        //TODO: por aqui o request a api para registar para depois mandar o user de volta para o login
        if(verificationsRegister(emailEditText.text.toString(),
                passwordEditText.text.toString(),
                dateEditText.text.toString())) {
                      
        }
    }
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