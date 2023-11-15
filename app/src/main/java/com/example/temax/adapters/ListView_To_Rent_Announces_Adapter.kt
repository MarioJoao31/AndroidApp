package com.example.temax.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.temax.R

class ListViewSellAnnounceAdapter(context: Context) :
    ArrayAdapter<ListViewSellAnnounceAdapter.SellAnnounce>(context, R.layout.custum_listview_annonces) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val sellAnnounces = mutableListOf<SellAnnounce>()

    data class SellAnnounce(
        val imageResource: Int,
        val postalCode: String,
        val description: String
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.custum_listview_annonces, parent, false)
        val item = sellAnnounces[position]
        val imageView = view.findViewById<ImageView>(R.id.image_sell_announce)
        val postalCodeTextView = view.findViewById<TextView>(R.id.text_postal_code)
        val descriptionTextView = view.findViewById<TextView>(R.id.text_description)

        imageView.setImageResource(item.imageResource)
        postalCodeTextView.text = item.postalCode
        descriptionTextView.text = item.description

        return view
    }

    fun addSellAnnounce(imageResource: Int, postalCode: String, description: String) {
        sellAnnounces.add(SellAnnounce(imageResource, postalCode, description))
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return sellAnnounces.size
    }
}
