package com.example.temax


import Comment
import CommentAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.temax.classes.Apartement
import com.example.temax.services.CommentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommentsScreen : AppCompatActivity() {
    private lateinit var commentService: CommentService
    private var houseID: Int = 0 // Variável de classe para armazenar houseID
    private var apartementID: Int = 0 // Variável de classe para armazenar houseID
    data class CreateComment(
        val userID: Int, // substitua pelo ID do usuário que está fazendo o comentário
        val commentText: String // substitua pelo texto do comentário
        // adicione outros campos conforme necessário pelo seu serviço
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments_screen)

        houseID = intent.getIntExtra("houseID", 0)
        apartementID = intent.getIntExtra("apartementID", 0)

        val listView = findViewById<ListView>(R.id.listview_comments)
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

        var ApartementID = apartementID
        // Callback para a resposta do serviço getCommentsByApartmentID()
        val callApartementComments = commentService.getCommentsByApartmentID(ApartementID)

        callApartementComments.enqueue(object : Callback<List<Comment>> {
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
                    // Tratamento para falha ao obter comentários por ApartmentID
                    Toast.makeText(
                        this@CommentsScreen,
                        "Estamos com problema ao carregar os comentários do apartamento",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                // Log de erro ou tratamento de falha na requisição para ApartmentID
                Log.e("CommentsScreen", "Erro ao carregar comentários do apartamento", t)

                // Tratamento para falha ao obter comentários por ApartmentID
                Toast.makeText(
                    this@CommentsScreen,
                    "Falha ao carregar comentários do apartamento",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }
}

