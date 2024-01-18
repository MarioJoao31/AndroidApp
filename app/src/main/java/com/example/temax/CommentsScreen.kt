package com.example.temax


import Comment
import CommentAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.temax.services.CommentService
import com.example.temax.services.UserService
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
    private lateinit var userService: UserService // Declare uma variável para o UserService

// Dentro do método onCreate() ou em outro lugar apropriado, configure o UserService

    private lateinit var listView: ListView
    private var houseID: Int = 0 // Variável de classe para armazenar houseID
    private var apartementID: Int = 0 // Variável de classe para armazenar apartID
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

        val userID = getSharedPreferences("Temax", Context.MODE_PRIVATE)
            .getString("userId", null)?.toIntOrNull() ?: -1 // -1 é um valor padrão

        houseID = intent.getIntExtra("houseID", 0)
        apartementID = intent.getIntExtra("apartementID", 0)
        roomID = intent.getIntExtra("roomID", 0)

        listView = findViewById<ListView>(R.id.listview_comments)

        val commentBaseUrl = "http://${BuildConfig.API_IP}:3000/house/rentHouses/"

        // Configuração do Retrofit para o commentService e userService (pode reutilizar a instância do Retrofit)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(commentBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        commentService = retrofit.create(CommentService::class.java)
        userService = retrofit.create(UserService::class.java)


        userService.getUserName(userID).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val userName = response.body()
                    Log.d("CommentAdapter", "Nome do Usuário recebido: $userName")
                } else if (response.code() == 404) {
                    Log.e("CommentAdapter", "Nome do usuário não encontrado (404)")
                } else {
                    Log.e("CommentAdapter", "Erro ao obter o nome do usuário: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("CommentAdapter", "Falha ao obter o nome do usuário", t)
            }
        })


        Log.d("CommentsScreen", "houseID: ${houseID}")

        var HouseID = houseID
        val callComments = commentService.getCommentsByHouseID(HouseID)

        // Callback para a resposta do serviço getRentHouses()
        callComments.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    val commentList = response.body()

                    if (commentList != null) {
                        Log.d("CommentsScreen", "Lista de comentários por HouseID: $commentList")
                        val adapter = CommentAdapter(
                            this@CommentsScreen,
                            R.layout.item_comment,
                            commentList,
                            userService
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
                // Log de erro para detalhes da exceção
                Log.e("CommentsScreen", "Falha ao enviar comentário", t)

                // Exibir uma mensagem de erro genérica para o usuário
                Toast.makeText(
                    this@CommentsScreen,
                    "Falha ao enviar comentário. Verifique sua conexão de internet.",
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
                            commentList,
                            userService
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

        var RoomID = roomID
        // Callback para a resposta do serviço getCommentsByApartmentID()
        val callRoomComments = commentService.getCommentsByRoomID(RoomID)

        callRoomComments.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    val commentList = response.body()

                    if (commentList != null) {
                        val adapter = CommentAdapter(
                            this@CommentsScreen,
                            R.layout.item_comment,
                            commentList,
                            userService
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

                // Limpar o texto da EditText após enviar o comentário
                editTextNewComment.setText("") // Define o texto como vazio
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
                    "Enviado com sucesso:",
                    Toast.LENGTH_SHORT
                ).show()

                // Limpar o EditText de comentário
                val editTextNewComment = findViewById<EditText>(R.id.edit_comment)
                editTextNewComment.setText("")

                // Atualizar a lista de comentários para HouseID e ApartmentID (chamadas da API)
                atualizarListaComentariosHouseID()
                atualizarListaComentariosApartmentID()
                atualizarListaComentariosRoomID()
            }
        })
    }

    private fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = Date()
        return sdf.format(currentDate) // Retorna a data e hora formatada
    }

    // Método para atualizar a lista de comentários pelo HouseID
    private fun atualizarListaComentariosHouseID() {
        val callComments = commentService.getCommentsByHouseID(houseID)

        callComments.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    val commentList = response.body()

                    if (commentList != null) {
                        val adapter = CommentAdapter(
                            this@CommentsScreen,
                            R.layout.item_comment,
                            commentList,
                            userService
                        )
                        listView.adapter = adapter
                    }
                } else {
                    // Tratamento para falha ao obter comentários por HouseID
                    Toast.makeText(
                        this@CommentsScreen,
                        "Estamos com problema ao carregar os comentários da casa",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                // Tratamento para falha ao obter comentários por HouseID
                Toast.makeText(
                    this@CommentsScreen,
                    "Falha ao carregar comentários da casa",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // Método para atualizar a lista de comentários pelo ApartmentID
    private fun atualizarListaComentariosApartmentID() {
        val callComments = commentService.getCommentsByApartmentID(apartementID)

        callComments.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    val commentList = response.body()

                    if (commentList != null) {
                        val adapter = CommentAdapter(
                            this@CommentsScreen,
                            R.layout.item_comment,
                            commentList,
                            userService
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
                // Tratamento para falha ao obter comentários por ApartmentID
                Toast.makeText(
                    this@CommentsScreen,
                    "Falha ao carregar comentários do apartamento",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun atualizarListaComentariosRoomID() {
        val callRoomComments = commentService.getCommentsByRoomID(roomID)

        callRoomComments.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful) {
                    val commentList = response.body()

                    if (commentList != null) {
                        val adapter = CommentAdapter(
                            this@CommentsScreen,
                            R.layout.item_comment,
                            commentList,
                            userService
                        )
                        listView.adapter = adapter
                    }
                } else {
                    // Tratamento para falha ao obter comentários por RoomID
                    Toast.makeText(
                        this@CommentsScreen,
                        "Estamos com problema ao carregar os comentários do quarto",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                // Log de erro ou tratamento de falha na requisição para RoomID
                Log.e("CommentsScreen", "Erro ao carregar comentários do quarto", t)

                // Tratamento para falha ao obter comentários por RoomID
                Toast.makeText(
                    this@CommentsScreen,
                    "Falha ao carregar comentários do quarto",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }




}

