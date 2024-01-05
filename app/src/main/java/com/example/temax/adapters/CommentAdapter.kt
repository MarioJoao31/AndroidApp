import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.temax.R

class CommentAdapter(
    context: Context,
    resource: Int,
    objects: List<Comment>
) : ArrayAdapter<Comment>(context, resource, objects) {

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
            vh.userName.text = "User ID: ${comment.userID}" // Exibindo o ID do usuário
            vh.commentText.text = comment.commentText
        }

        return view
    }

    private class CommentViewHolder(
        val userName: TextView,
        val commentText: TextView
    )
}
