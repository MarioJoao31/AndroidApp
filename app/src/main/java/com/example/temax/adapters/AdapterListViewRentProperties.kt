package com.example.temax.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.temax.R
import com.example.temax.classes.Apartement
import com.example.temax.classes.House

class AdapterListViewRentProperties(
    context: Context,
    resource: Int,
    objects: List<Any>
) : ArrayAdapter<Any>(context, resource, objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val vh: MyViewHolder

        //serve para melhorar o processo de memoria e de guardar as list views que ja passaram
        if (convertView != null) {
            view = convertView
        } else {
            // expand a view do layout  para cada item da lista
            view = LayoutInflater.from(context).inflate(R.layout.custum_listview_annonces, parent, false)
            view.tag = MyViewHolder(view)
        }

        //serve para fazer referencia a class abaixo
        vh = view.tag as MyViewHolder

        //serve para ir buscar os valores da lista
        val property = getItem(position)

        val rentRegex = Regex("rent", RegexOption.IGNORE_CASE)

        if (property is House) {
            //  Logica  para House
            if (rentRegex.containsMatchIn(property.ListingType)) {
                vh.imagem?.setBackgroundColor(Color.RED) // Cor diferente para casas para alugar
            } else {
                vh.imagem?.setBackgroundColor(Color.GREEN) // Cor padrão para casas
            }
            vh.price?.text = property.Price.toString()
            vh.descricao?.text = property.Description
            vh.elevator?.text = property.Elevator
            vh.wcs?.text = property.WCs.toString()
            vh.totalArea?.text = property.Total_lot_area.toString()

        } else if (property is Apartement) {
            //  Logica  para Apartement
            if (rentRegex.containsMatchIn(property.ListingType)) {
                vh.imagem?.setBackgroundColor(Color.BLUE) // Cor diferente para apartamentos para alugar
            } else {
                vh.imagem?.setBackgroundColor(Color.GREEN) // Cor padrão para apartamentos
            }
            vh.price?.text = property.Price.toString()
            vh.descricao?.text = property.Description
            vh.elevator?.text = property.Elevator
            vh.wcs?.text = property.WCs.toString()
        }

        return view
    }

    // Classe interna para armazenar as views
    private class MyViewHolder(view: View?) {
        val imagem = view?.findViewById<ImageView>(R.id.image_sell_announce)
        val price = view?.findViewById<TextView>(R.id.text_price)
        val wcs = view?.findViewById<TextView>(R.id.text_WCs)
        val totalArea = view?.findViewById<TextView>(R.id.text_total_area)
        val descricao = view?.findViewById<TextView>(R.id.text_description)
        val elevator = view?.findViewById<TextView>(R.id.text_elevator)
    }
}