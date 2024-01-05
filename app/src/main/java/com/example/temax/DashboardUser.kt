package com.example.temax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class DashboardUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_user)
    }

    fun logoutOnClick(view: View) {

        // Obtens os dados que estão no SharedPreferences
        val sharedPreferences = getSharedPreferences("Temax", MODE_PRIVATE)

        //permite editar e remover o que se encontra no shared preferences
        val editor = sharedPreferences.edit()
        editor.remove("userId")
        editor.remove("token")
        editor.apply()

        // Redireciona para a tela de login (MainActivity)
        val intent = Intent(this, MainActivity::class.java)

        // Limpar a pilha de atividades e iniciar a MainActivity
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)

        finish() // Fecha a atividade atual

    }
}