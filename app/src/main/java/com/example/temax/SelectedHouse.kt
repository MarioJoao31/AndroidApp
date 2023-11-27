package com.example.temax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.temax.classes.Apartement
import com.example.temax.classes.House
import kotlinx.coroutines.selects.select
import java.io.Serializable

class SelectedHouse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_house)

        val selectedItem = intent.getSerializableExtra("selectedItem") as? Serializable

        //criar as variaveis
        var price: Double ? = 0.0
        var description: String ? = ""
        var wcs: Int ? = 0
        var postal_code : String ?= ""
        var construction_year: Int ? = 0
        var parking: Int ? = 0
        var elevator: String ? = ""
        var private_gross_area: Int ? = 0
        var total_lot_area: Int ? = 0
        var bedrooms:Int ? = 0
        var tittle: String ? = ""
        var address: String ? = ""

        //atribui os valores
        if (selectedItem != null) {
            if (selectedItem is House) {
                price = selectedItem.Price ?: 0.0
                description = selectedItem.Description
                wcs = selectedItem.WCs ?: 0
                postal_code = selectedItem.Postal_code
                construction_year = selectedItem.Construction_year
                parking = selectedItem.Parking
                elevator = selectedItem.Elevator
                private_gross_area = selectedItem.Private_gross_area
                total_lot_area = selectedItem.Total_lot_area
                bedrooms = selectedItem.Bedrooms
                tittle = selectedItem.Title
                address = selectedItem.Address
            }
            if (selectedItem is Apartement) {

            }
        }

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

        //////////////////////////////////
        ////////// Visibilidade do screen
        //////////////////////////////////

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
        // Configure outros TextViews conforme necessário
    }
}