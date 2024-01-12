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
import com.example.temax.classes.HouseEntity
import com.example.temax.classes.Room

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
            vh.title?.text = property.Title
            vh.price?.text = property.Price.toString()
            vh.descricao?.text = property.Description
            vh.elevator?.text = property.Elevator
            vh.parking?.text = property.Parking.toString()
            vh.wcs?.text = property.WCs.toString()
            vh.totalArea?.text = property.Total_lot_area.toString()

        } else if (property is Apartement) {
            //  Logica  para Apartement
            if (rentRegex.containsMatchIn(property.ListingType)) {
                vh.imagem?.setBackgroundColor(Color.BLUE) // Cor diferente para apartamentos para alugar
            } else {
                vh.imagem?.setBackgroundColor(Color.GREEN) // Cor padrão para apartamentos
            }

            vh.title?.text = property.Title
            vh.price?.text = property.Price.toString()
            vh.descricao?.text = property.Description
            vh.elevator?.text = property.Elevator
            vh.parking?.text = property.Parking.toString()
            vh.wcs?.text = property.WCs.toString()
            vh.totalArea?.text = "Indefinido"

        }else if (property is Room) {
            //  Lógica para Room
            if (rentRegex.containsMatchIn(property.ListingType)) {
                vh.imagem?.setBackgroundColor(Color.YELLOW) // Cor diferente para quartos para alugar
            } else {
                vh.imagem?.setBackgroundColor(Color.GREEN) // Cor padrão para quartos
            }

            vh.title?.text = property.Title
            vh.price?.text = property.Price.toString()
            vh.descricao?.text = property.Description
            vh.elevator?.text = property.Elevator
            vh.parking?.text = "Indefinido"
            vh.wcs?.text = property.Private_wc.toString()
            vh.totalArea?.text = "Indefinido"
        }else if (property is HouseEntity){
            //  Logica  para House
            if (rentRegex.containsMatchIn(property.listingType)) {
                vh.imagem?.setBackgroundColor(Color.RED) // Cor diferente para casas para alugar
            } else {
                vh.imagem?.setBackgroundColor(Color.GREEN) // Cor padrão para casas
            }
            vh.title?.text = property.title
            vh.price?.text = property.price.toString()
            vh.descricao?.text = property.description
            vh.elevator?.text = property.elevator
            vh.parking?.text = property.parking.toString()
            vh.wcs?.text = property.wcs.toString()
            vh.totalArea?.text = property.totalLotArea.toString()
        }/*else if (property is ApartementEntity){
            //  Logica  para Apartement
            if (rentRegex.containsMatchIn(property.listingType)) {
                vh.imagem?.setBackgroundColor(Color.BLUE) // Cor diferente para apartamentos para alugar
            } else {
                vh.imagem?.setBackgroundColor(Color.GREEN) // Cor padrão para apartamentos
            }

            vh.title?.text = property.title
            vh.price?.text = property.price.toString()
            vh.descricao?.text = property.description
            vh.elevator?.text = property.elevator
            vh.parking?.text = property.parking.toString()
            vh.wcs?.text = property.wcs.toString()
            vh.totalArea?.text = "Indefinido"
        }*/

        // Aqui adicionamos a lógica para Prioraty_level e a visibilidade da TextView txtStar
        val txtStar = view.findViewById<TextView>(R.id.text_star)

        // Verifique se o Prioraty_level é igual a 1
        if ((property is House && property.Prioraty_level == 1)
            || (property is Apartement && property.Prioraty_level == 1)
            || (property is Room && property.Prioraty_level == 1)
            || (property is HouseEntity && property.priorityLevel == 1)) {
            // Se sim, torne a TextView visível
            txtStar.visibility = View.VISIBLE
        } else {
            // Caso contrário, torne a TextView invisível
            txtStar.visibility = View.INVISIBLE
        }

        return view
    }

    // Classe interna para armazenar as views
    private class MyViewHolder(view: View?) {
        val imagem = view?.findViewById<ImageView>(R.id.image_sell_announce)
        val title = view?.findViewById<TextView>(R.id.text_title)
        val price = view?.findViewById<TextView>(R.id.text_price)
        val wcs = view?.findViewById<TextView>(R.id.text_WCs)
        val totalArea = view?.findViewById<TextView>(R.id.text_total_area)
        val descricao = view?.findViewById<TextView>(R.id.text_description)
        val elevator = view?.findViewById<TextView>(R.id.text_elevator)
        val parking = view?.findViewById<TextView>(R.id.text_ParkingVariable)
    }
}