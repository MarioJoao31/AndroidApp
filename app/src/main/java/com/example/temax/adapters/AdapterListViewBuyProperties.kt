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
import com.example.temax.classes.House

class AdapterListViewBuyProperties(context: Context,resource: Int, objects: List<House> ) :
    ArrayAdapter<House>(context, resource,objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val listHouses = mutableListOf<House>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val vh: MyViewHolder

        //serve para melhorar o processo de memoria e de guardar as list views que ja passaram
        if(convertView != null){
            view = convertView
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.custum_listview_annonces,parent,false)
            view.tag = MyViewHolder(view)
        }

        //serve para fazer referencia a class abaixo
        vh = view.tag as MyViewHolder
        //serve para ir buscar os valores da lista
        val house = getItem(position)

        //verificação se os valores não forem nullos
        if(house != null){
            if (house.ListingType == "sell" && house.ListingType == "Sell" ){
                vh.imagem?.setBackgroundColor(Color.GREEN)
            }else{
                vh.imagem?.setBackgroundColor(Color.RED)
            }
            vh.price?.text = house.Price.toString()
            vh.descricao?.text = house.Description
            vh.elevator?.text = house.Elevator
            vh.wcs?.text = house.WCs.toString()
            vh.totalArea?.text = house.Total_lot_area.toString()
        }

        return view
    }

}

//serve para guardar as views
private class MyViewHolder(view: View?){
    //image
    val imagem = view?.findViewById<ImageView>(R.id.image_sell_announce)
    //codigo postal
    val price = view?.findViewById<TextView>(R.id.text_price)

    val wcs = view?.findViewById<TextView>(R.id.text_WCs)
    //area total
    val totalArea = view?.findViewById<TextView>(R.id.text_total_area)
    //descricao
    val descricao = view?.findViewById<TextView>(R.id.text_description)

    val elevator = view?.findViewById<TextView>(R.id.text_elevator)
}