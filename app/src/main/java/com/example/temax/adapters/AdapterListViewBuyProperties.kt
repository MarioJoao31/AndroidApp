package com.example.temax.adapters

import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import com.example.temax.R

class AdapterListViewBuyProperties(context: Context) :
    ArrayAdapter<AdapterListViewBuyProperties>(context, R.layout.custum_listview_annonces) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val sellAnnounces = mutableListOf<ListViewSellAnnounceAdapter.SellAnnounce>()

}