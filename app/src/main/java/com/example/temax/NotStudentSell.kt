package com.example.temax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import com.example.temax.adapters.SpinnerItem
import com.example.temax.adapters.Spinner_Sell_Adapter

class NotStudentSell : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_student_sell)


        val spinner = findViewById<Spinner>(R.id.myspinner)

        //visibilidade NA opçao de Room
        val etAvailableKitchen = findViewById<EditText>(R.id.EtAvailableKitchen)
        val etPrivateWc = findViewById<EditText>(R.id.EtPrivateWc)
        val etNumBeds = findViewById<EditText>(R.id.EtNumBeds)
        val etSharedRoom = findViewById<EditText>(R.id.EtSharedRoom)

        //visibilidade na house

        val etTotalLotArea = findViewById<EditText>(R.id.EtTotalLotArea)
        val etPrivateGrossArea = findViewById<EditText>(R.id.EtPrivateGrossArea)
        val etWcs = findViewById<EditText>(R.id.EtWcs)
        val etBedrooms = findViewById<EditText>(R.id.EtBedrooms)

        //visibilidade na flat

        val etFloor = findViewById<EditText>(R.id.EtFloor)

        // ... adicione mais referências para suas EditTexts conforme necessário

        val items = listOf(
            SpinnerItem("Apartment", R.mipmap.ic_flat),
            SpinnerItem("Residence", R.mipmap.ic_house),
            SpinnerItem("Room", R.mipmap.ic_room),
        )

        val adapter = Spinner_Sell_Adapter(this, items)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = items[position].text

                //Room
                etAvailableKitchen.visibility = View.GONE
                etPrivateWc.visibility = View.GONE
                etNumBeds.visibility = View.GONE
                etSharedRoom.visibility = View.GONE
                //House
                etTotalLotArea.visibility = View.GONE
                etPrivateGrossArea.visibility = View.GONE
                etWcs.visibility = View.GONE
                etBedrooms.visibility = View.GONE
                //Flat
                etFloor.visibility = View.GONE
                // ... oculte outras EditTexts conforme necessário

                // Mostra as EditTexts com base na opção selecionada no Spinner
                when (selectedItem) {
                    "Apartment" -> {
                        // ... mostre outras EditTexts conforme necessário para "Apartment"
                        etFloor.visibility = View.VISIBLE
                        etWcs.visibility = View.VISIBLE
                        etBedrooms.visibility = View.VISIBLE
                    }
                    "Residence" -> {
                        // Mostrar as EditTexts necessárias para "Residence"
                        etTotalLotArea.visibility = View.VISIBLE
                        etPrivateGrossArea.visibility = View.VISIBLE
                        etWcs.visibility = View.VISIBLE
                        etBedrooms.visibility = View.VISIBLE
                    }
                    "Room" -> {
                        etAvailableKitchen.visibility = View.VISIBLE
                        etPrivateWc.visibility = View.VISIBLE
                        etNumBeds.visibility = View.VISIBLE
                        etSharedRoom.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Implemente conforme necessário
            }
        }
    }
}
