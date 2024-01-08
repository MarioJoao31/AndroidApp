package com.example.temax


import Comment
import CommentAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.temax.services.CommentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CommentsScreen : AppCompatActivity() {
    private lateinit var commentService: CommentService
    private var houseID: Int = 0 // Variável de classe para armazenar houseID
    private var apartementID: Int = 0 // Variável de classe para armazenar apartID
    private var userID: Int = 0
    private var roomID: Int = 0

    // Renomeie a classe para CreateCommentData
    data class CreateCommentData(
        val UserID: Int,
        val Coment_Text: String,
        val Coment_Datetime: String,
        val RoomID: Int?,
        val ApartementID: Int?,
        val HouseID: Int?
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments_screen)

        houseID = intent.getIntExtra("houseID", 0)
        apartementID = intent.getIntExtra("apartementID", 0)
        userID = intent.getIntExtra("userID", 0)

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

        val editTextNewComment = findViewById<EditText>(R.id.edit_comment)
        val buttonSendComment = findViewById<Button>(R.id.btn_send_comment)

        buttonSendComment.setOnClickListener {
            val commentText = editTextNewComment.text.toString()

            // Aqui você pode enviar o novo comentário
            if (commentText.isNotEmpty()) {
                enviarNovoComentario(userID, commentText, roomID, apartementID, houseID)
            } else {
                Toast.makeText(
                    this@CommentsScreen,
                    "Digite um comentário antes de enviar.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun enviarNovoComentario(userCommentID: Int, commentText: String, roomID: Int, apartementID: Int, houseID: Int) {
        val currentDateTime = getCurrentDateTime() // Obtém a data e hora atual

        // Verificação para enviar null se o roomID for 0
        val roomIDToSend = if (roomID == 0) null else roomID

        // Verificação para enviar null se o apartementID for 0
        val apartementIDToSend = if (apartementID == 0) null else apartementID

        // Verificação para enviar null se o houseID for 0
        val houseIDToSend = if (houseID == 0) null else houseID

        val createComment = CreateCommentData(userCommentID, commentText, currentDateTime, roomIDToSend, apartementIDToSend, houseIDToSend)

        // Log para verificar o objeto createComment antes de enviar a solicitação
        Log.d("CommentsScreen", "createComment: $createComment")

        val callCreateComment = commentService.createComment(createComment)

        callCreateComment.enqueue(object : Callback<Comment> {
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                val code = response.code()
                if (response.isSuccessful) {
                    Log.d("CommentsScreen", "Comentário enviado com sucesso. Código: $code")
                    Toast.makeText(
                        this@CommentsScreen,
                        "Comentário criado com sucesso.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e("CommentsScreen", "Erro ao enviar comentário. Código: $code")
                    Toast.makeText(
                        this@CommentsScreen,
                        "Erro ao enviar comentário. Código: $code",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                // Log de erro ou tratamento de falha na requisição para enviar o comentário
                Log.e("CommentsScreen", "Falha ao enviar comentário", t)

                // Tratamento para falha ao enviar o comentário
                Toast.makeText(
                    this@CommentsScreen,
                    "Falha ao enviar comentário: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = Date()
        return sdf.format(currentDate) // Retorna a data e hora formatada
    }
}

