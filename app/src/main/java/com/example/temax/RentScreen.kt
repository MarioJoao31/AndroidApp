package com.example.temax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.temax.adapters.ListViewSellAnnounceAdapter

class RentScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_screen)

        // Dentro da sua Activity ou Fragment
        val listView = findViewById<ListView>(R.id.listview_rent_announces)

        val adapter = ListViewSellAnnounceAdapter(this)
        adapter.addSellAnnounce(R.mipmap.ic_buyhouse, "12345-678", "Linda casa com 3 quartos")
        adapter.addSellAnnounce(R.mipmap.ic_rent, "54321-876", "Apartamento moderno no centro")

        listView.adapter = adapter

    }
}