package com.example.temax


import Comment
import CommentAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.temax.services.CommentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommentsScreen : AppCompatActivity() {
    private lateinit var commentService: CommentService

    data class CreateComment(
        val userID: Int, // substitua pelo ID do usuário que está fazendo o comentário
        val commentText: String // substitua pelo texto do comentário
        // adicione outros campos conforme necessário pelo seu serviço
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments_screen)

        val houseID = intent.getIntExtra("houseID", 0)

        val listView = findViewById<ListView>(R.id.listview_comments)
        val btnSendComment = findViewById<Button>(R.id.btn_send_comment)
        val commentBaseUrl = "http://${BuildConfig.API_IP}:3000/house/rentHouses/"

        // Configuração do Retrofit para o commentService
        val commentRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(commentBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        commentService = commentRetrofit.create(CommentService::class.java)

        Log.d("CommentsScreen", "houseID: ${houseID}")

        var HouseID = houseID
        val callComments = commentService.getCommentsByHouseID(HouseID)

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
                    Toast.makeText(
                        this@CommentsScreen,
                        "Estamos com problema ao carregar os comentários",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                // Log de erro ou tratamento de falha na requisição
                // Aqui você pode registrar detalhes sobre o erro para ajudar na depuração do problema
                Log.e("CommentsScreen", "Erro ao carregar comentários", t)

                // Também é possível exibir uma mensagem ao usuário, informando sobre o problema
                Toast.makeText(
                    this@CommentsScreen,
                    "Falha ao carregar comentários",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }



}
