package com.example.temax

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.temax.services.UserService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditUserProfile : AppCompatActivity() {

    private lateinit var userService: UserService // Declare uma variável para o UserService
    private lateinit var editTextName: EditText
    private lateinit var editTextContact: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editButton: Button

    private var userID = -1 // -1 é um valor padrão
    private val baseUrl = "http://${BuildConfig.API_IP}:3000/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_profile)

        // Recupere o userID dentro do método onCreate
        userID = getSharedPreferences("Temax", Context.MODE_PRIVATE)
            .getString("userId", null)?.toIntOrNull() ?: -1 // -1 é um valor padrão

        Log.d("EditUserProfile", "User ID: $userID")

        // Referencie as Views
        editTextName = findViewById(R.id.editTextName)
        editTextContact = findViewById(R.id.editTextContact)
        editTextPassword = findViewById(R.id.editTextPassword)
        editButton = findViewById(R.id.btEditar)

        // Configuração do Retrofit para o commentService e userService (pode reutilizar a instância do Retrofit)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userService = retrofit.create(UserService::class.java)

        // Adicione um listener ao botão
        editButton.setOnClickListener {
            // Obtenha os valores dos EditTexts
            val name = editTextName.text.toString()
            val contact = editTextContact.text.toString()
            val password = editTextPassword.text.toString()

            // Chame a lógica de atualização aqui (por exemplo, usando Retrofit)
            // Substitua isso com sua lógica real
            updateProfile(name, contact, password)
            Log.d("EditUserProfile", "Update Data - Name: $name, Contact: $contact, Password: $password")
        }
    }

    private fun updateProfile(name: String?, contact: String?, password: String?) {
        try {
            // Log para verificar os dados antes de fazer a chamada Retrofit
            Log.d("EditUserProfile", "Updating profile - Name: $name, Contact: $contact, Password: $password")

            // Crie um mapa para os dados de atualização
            val updateData = mutableMapOf<String, String?>()

            // Adicione apenas os campos que foram preenchidos
            if (!name.isNullOrBlank()) {
                updateData["Name"] = name
            }

            if (!contact.isNullOrBlank()) {
                updateData["Contact"] = contact
            }

            if (!password.isNullOrBlank()) {
                updateData["Password"] = password
            }

            // Chame o método de atualização do UserService
            val userId = userID  // Substitua com o ID real do usuário
            val call: Call<Any> = userService.updateProfile(userId, updateData)

            // Faça a chamada assíncrona
            call.enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    try {
                        if (response.isSuccessful) {
                            Log.d("EditUserProfile", "Request URL: ${call.request().url}")
                            Log.d("EditUserProfile", "Request Body: ${Gson().toJson(updateData)}")
                            // Lide com a resposta bem-sucedida aqui
                            Log.d("EditUserProfile", "Perfil atualizado com sucesso")
                            Toast.makeText(applicationContext, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                            // Limpe os EditTexts após a atualização bem-sucedida
                            editTextName.text.clear()
                            editTextContact.text.clear()
                            editTextPassword.text.clear()
                        } else {
                            // Lide com uma resposta de erro aqui
                            val errorMessage = response.errorBody()?.string()
                            Log.e("EditUserProfile", "Erro ao atualizar o perfil. Código: ${response.code()}, Mensagem: $errorMessage")
                            Toast.makeText(applicationContext, "Erro ao atualizar o perfil", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("EditUserProfile", "Exceção durante o tratamento da resposta: ${e.message}")
                        Toast.makeText(applicationContext, "Erro durante o processamento da resposta", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    // Lide com falha na chamada aqui
                    Log.e("EditUserProfile", "Falha na chamada de atualização", t)
                    Toast.makeText(applicationContext, "Falha na chamada de atualização", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Log.e("EditUserProfile", "Exceção durante a atualização do perfil: ${e.message}")
            Toast.makeText(applicationContext, "Erro durante a atualização do perfil", Toast.LENGTH_SHORT).show()
        }
    }
}