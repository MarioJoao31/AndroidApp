package com.example.temax


import Comment
import CommentAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.temax.adapters.AdapterListViewRentProperties
import com.example.temax.classes.House
import com.example.temax.services.CommentService
import com.example.temax.services.HouseServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommentsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments_screen)


        val listView = findViewById<ListView>(R.id.listview_comments)

        val commentBaseUrl = "http://${BuildConfig.API_IP}:3000/house/rentHouses/"

        // Configuração do Retrofit para o commentService
        val commentRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(commentBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val commentService = commentRetrofit.create(CommentService::class.java)

        val callComments = commentService.getAllComments()

        // Callback para a resposta do serviço getRentHouses()
        callComments.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    val commentList = response.body()

                    if (commentList != null) {
                        val adapter = CommentAdapter(
                            this@CommentsScreen,
                            R.layout.item_comment,
                            commentList
                        )
                        listView.adapter = adapter
                    }
                } else {
                    // Também é possível exibir uma mensagem ao usuário, informando sobre o problema
                    Toast.makeText(this@CommentsScreen, "Estamos Com problema a carregar os coments", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                // Log de erro ou tratamento de falha na requisição
                // Aqui você pode registrar detalhes sobre o erro para ajudar na depuração do problema
                Log.e("CommentsScreen", "Erro ao carregar comentários", t)

                // Também é possível exibir uma mensagem ao usuário, informando sobre o problema
                Toast.makeText(this@CommentsScreen, "Falha ao carregar comentários", Toast.LENGTH_SHORT).show()
            }
        })

    }
}