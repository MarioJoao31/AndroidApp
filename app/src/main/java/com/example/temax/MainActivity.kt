package com.example.temax

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

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
        Toast.makeText(this, emailEditText.text.toString(), Toast.LENGTH_SHORT).show()

        val intent = Intent(this@MainActivity, SelectTypeUser::class.java)
        startActivityForResult(intent,1)

    }

    fun main_buttonRegister(view: View){
        val intent = Intent(this@MainActivity, Register::class.java)
        startActivityForResult(intent,1)
    }

    fun validate(emailAdrees: String, password: String):Boolean{
        //TODO: fazer aqui depois o request a API para ver se tem permissao

        return true;
    }
}