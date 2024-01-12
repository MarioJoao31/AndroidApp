import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.temax.R
import com.example.temax.classes.Payment

class AdapterListViewPayments(
    context: Context,
    resource: Int,
    objects: List<Payment> // Substitua "Payment" pelo tipo real dos seus objetos
): ArrayAdapter<Payment>(context, resource, objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View
        val vh: MyViewHolder

        // Serve para melhorar o processo de memória e armazenar as list views que já passaram
        if (convertView != null) {
            view = convertView
            vh = view.tag as MyViewHolder
        } else {
            // Expande a view do layout para cada item da lista
            view = inflater.inflate(R.layout.custom_listview_payments, parent, false)
            vh = MyViewHolder(view)
            view.tag = vh
        }

        // Configurar os dados para a posição atual
        val payment = getItem(position)

        vh.textViewPrice.text = "Price: ${payment?.Price}"
        vh.textViewStatus.text = "Status: ${payment?.Status}"
        vh.textViewTypePayment.text = "Type Payment: ${payment?.Type_Payment}"
        vh.textViewDate.text = "Date: ${payment?.Date}"

        return view
    }

    // ViewHolder para armazenar as views
    private class MyViewHolder(view: View) {
        val textViewPrice = view.findViewById<TextView>(R.id.textViewPrice)
        val textViewStatus = view.findViewById<TextView>(R.id.textViewStatus)
        val textViewTypePayment = view.findViewById<TextView>(R.id.textViewTypePayment)
        val textViewDate = view.findViewById<TextView>(R.id.textViewDate)
    }
}
