package com.example.temax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.temax.adapters.AdapterListViewBuyProperties
import com.example.temax.adapters.ListViewSellAnnounceAdapter
import com.example.temax.classes.House

class RentScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_screen)

        // Dentro da sua Activity ou Fragment
        val listView = findViewById<ListView>(R.id.listview_rent_announces)

        //dados para a lista
        //var house1 = House(1,2,123.0,2001, 2,"sim",3,"descricao,","4710-306",55,20,2,"Rent")

        //val values = mutableListOf<House>(house1,house2)
        //val adapter = AdapterListViewBuyProperties(this,R.layout.custum_listview_annonces,values)

        //listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            // Iniciar a atividade SelectedHouse e passar os detalhes da casa selecionada através de Intent extras
            val intent = Intent(this@RentScreen, SelectedHouse::class.java)
            startActivity(intent)
        }

    }




}

