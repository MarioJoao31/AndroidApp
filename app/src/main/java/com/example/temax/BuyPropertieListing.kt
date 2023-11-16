package com.example.temax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.temax.adapters.AdapterListViewBuyProperties
import com.example.temax.classes.House

class BuyPropertieListing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_propertie_listing)

        // Dentro da sua Activity ou Fragment
        val listView = findViewById<ListView>(R.id.listviewBuyProperties)

        //dados para a lista
        var house1 = House(1,2,123.0,2001, 2,"sim",3,"descricao,","4710-306",55,20,2,"Sell")
        var house2 = House(1,2,123.0,2001, 2,"sim",3,"descricao,","4710-306",55,20,2,"Sell")

        val values = mutableListOf<House>(house1,house2)
        val adapter = AdapterListViewBuyProperties(this,R.layout.custum_listview_annonces,values)

        listView.adapter = adapter
    }
}