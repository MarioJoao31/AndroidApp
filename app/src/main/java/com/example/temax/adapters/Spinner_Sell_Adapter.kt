package com.example.temax.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.temax.R

data class SpinnerItem(val text: String, val imageResId: Int)
class Spinner_Sell_Adapter (context: Context, private val items: List<SpinnerItem>) :
    ArrayAdapter<SpinnerItem>(context, R.layout.custom_spinner_sell_item, items){

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.custom_spinner_sell_item, parent, false)
        val item = getItem(position)
        val textView = view.findViewById<TextView>(R.id.spinnerItemText)
        val imageView = view.findViewById<ImageView>(R.id.spinnerItemImage)

        textView.text = item?.text
        imageView.setImageResource(item?.imageResId ?: 0)

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

}