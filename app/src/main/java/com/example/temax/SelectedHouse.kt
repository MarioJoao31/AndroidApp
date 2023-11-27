package com.example.temax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SelectedHouse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_house)

        // Recuperar os extras passados pelo Intent
        val price = intent.getDoubleExtra("price", 0.0)
        val description = intent.getStringExtra("description")
        val wcs = intent.getIntExtra("wcs", 0)
        val postal_code = intent.getStringExtra("postal_code")
        var construction_year = intent.getIntExtra("construction_year",0)
        var parking = intent.getIntExtra("parking",0)
        var elevator = intent.getStringExtra("elevator")
        var private_gross_area = intent.getIntExtra("private_gross_area",0)
        var total_lot_area = intent.getIntExtra("private_gross_area",0)
        var bedrooms = intent.getIntExtra("bedrooms",0)
        var tittle = intent.getStringExtra("tittle")
        var address = intent.getStringExtra("address")
        // Recupere outros extras conforme necessário

        // Encontre os TextViews na sua UI
        val textViewPrice = findViewById<TextView>(R.id.textViewPrice)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val text_WCs = findViewById<TextView>(R.id.textViewWCs)
        val textViewPostalCode = findViewById<TextView>(R.id.textViewPostalCode)
        val textViewConstructionYear = findViewById<TextView>(R.id.textViewConstructionYear)
        val textViewParking = findViewById<TextView>(R.id.textViewParking)
        val textViewElevator = findViewById<TextView>(R.id.textViewElevator)
        val textViewPrivateGrossArea = findViewById<TextView>(R.id.textViewPrivateGrossArea)
        val textViewTotalLotArea = findViewById<TextView>(R.id.textViewTotalLotArea)
        val textViewBedrooms = findViewById<TextView>(R.id.textViewBedrooms)
        val textViewTittle = findViewById<TextView>(R.id.textViewTittle)
        val textViewAddress = findViewById<TextView>(R.id.textViewAddress)
        // Encontre outros TextViews conforme necessário

        // Configure os valores nos TextViews
        if (price != null) {
            textViewPrice.text = "$price"
        } else {
            // Lógica para lidar com o valor nulo, por exemplo, definir um texto padrão ou vazio
            textViewPrice.text = "Valor não disponível"
        }
        textViewDescription.text = "$description"
        text_WCs.text = "$wcs"
        textViewPostalCode.text ="$postal_code"
        textViewConstructionYear.text = "$construction_year"
        textViewParking.text = "$parking"
        textViewElevator.text = "$elevator"
        textViewPrivateGrossArea.text = "$private_gross_area"
        textViewTotalLotArea.text = "$total_lot_area"
        textViewBedrooms.text = "$bedrooms"
        textViewTittle.text = "$tittle"
        textViewAddress.text = "$address"
        // Configure outros TextViews conforme necessári
    }
}