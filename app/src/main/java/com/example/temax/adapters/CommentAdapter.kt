import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.temax.R
import com.example.temax.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentAdapter(
    context: Context,
    resource: Int,
    private val comments: List<Comment>,
    private val userService: UserService // Adicione isso como parâmetro
) : ArrayAdapter<Comment>(context, resource, comments) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val vh: CommentViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.item_comment, parent, false)
            vh = CommentViewHolder(
                view.findViewById(R.id.text_user_name),
                view.findViewById(R.id.text_comment)
            )
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as CommentViewHolder
        }

        val comment = getItem(position)

        if (comment != null) {
            vh.commentText.text = comment.commentText

            Log.d("CommentAdapter", "Chamando o serviço para userID: ${comment.userID}")

            // Chame a função getUserName para obter o nome do usuário usando o serviço Retrofit
            getUserName(comment.userID, userService, vh)
        }

        return view
    }

    // Função para obter o nome do usuário usando o serviço Retrofit
    private fun getUserName(userID: Int, userService: UserService, vh: CommentViewHolder) {
        userService.getUserName(userID).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val userName = response.body()
                    vh.userName.text = "Nome do Usuário: $userName"
                    Log.d("CommentAdapter", "Nome do Usuário recebido: $userName")
                } else if (response.code() == 404) {
                    vh.userName.text = "Nome do Usuário: Não encontrado"
                    Log.e("CommentAdapter", "Nome do usuário não encontrado (404)")
                } else {
                    vh.userName.text = "Nome do Usuário: Desconhecido"
                    Log.e("CommentAdapter", "Erro ao obter o nome do usuário: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                vh.userName.text = "Nome do Usuário: Desconhecido"
                Log.e("CommentAdapter", "Falha ao obter o nome do usuário", t)
            }
        })
    }

    private class CommentViewHolder(
        val userName: TextView,
        val commentText: TextView
    )
}

