package com.example.temax.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.temax.R
import com.example.temax.classes.Apartement
import com.example.temax.classes.House
import com.example.temax.classes.Room
import java.io.Serializable

class AdapterListViewBuyProperties(context: Context,resource: Int, objects: MutableList<Serializable> ) :
    ArrayAdapter<Serializable>(context, resource,objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        //serve para melhorar o processo de memoria e de guardar as list views que ja passaram
        if(convertView != null){
            view = convertView
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.custum_listview_annonces,parent,false)
            view.tag = MyViewHolder(view)
        }

        //serve para fazer referencia a class abaixo
        val vh: MyViewHolder = view.tag as MyViewHolder
        //serve para ir buscar os valores da lista
        val propertie = getItem(position)

        //verificação se os valores não forem nullos
        when (propertie) {
            is House -> {
                // If it's a House
                vh.price?.text = propertie.Price.toString()
                vh.descricao?.text = propertie.Description
                vh.elevator?.text = propertie.Elevator
                vh.wcs?.text = propertie.WCs.toString()
                vh.totalArea?.text = propertie.Total_lot_area.toString()
            }
            is Apartement -> {
                // If it's an Apartment (assuming you have an Apartment class)
                vh.price?.text = propertie.Price.toString()
                vh.descricao?.text = propertie.Description
                vh.elevator?.text = propertie.Elevator
                vh.wcs?.text = propertie.WCs.toString()
                // Add any additional properties specific to Apartment
            }
            else -> {
                // Handle other types if needed
                vh.price?.text = ""
                vh.descricao?.text = ""
                vh.elevator?.text = ""
                vh.wcs?.text = ""
                vh.totalArea?.text = ""
            }
        }

        // Aqui adicionamos a lógica para Prioraty_level e a visibilidade da TextView txtStar
        val txtStar = view.findViewById<TextView>(R.id.text_star)

        // Verifique se o Prioraty_level é igual a 1
        if ((propertie is House && propertie.Prioraty_level == 1)
            || (propertie is Apartement && propertie.Prioraty_level == 1)
            || (propertie is Room && propertie.Prioraty_level == 1)) {
            // Se sim, torne a TextView visível
            txtStar.visibility = View.VISIBLE
        } else {
            // Caso contrário, torne a TextView invisível
            txtStar.visibility = View.INVISIBLE
        }

        return view
    }


}

//serve para guardar as views
private class MyViewHolder(view: View?){
    //codigo postal
    val price = view?.findViewById<TextView>(R.id.text_price)

    val wcs = view?.findViewById<TextView>(R.id.text_WCs)
    //area total
    val totalArea = view?.findViewById<TextView>(R.id.text_total_area)
    //descricao
    val descricao = view?.findViewById<TextView>(R.id.text_description)

    val elevator = view?.findViewById<TextView>(R.id.text_elevator)
}