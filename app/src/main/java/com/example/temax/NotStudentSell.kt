package com.example.temax

import android.content.Context
import android.content.SharedPreferences
import android.media.tv.AdRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import com.example.temax.adapters.SpinnerItem
import com.example.temax.adapters.Spinner_Sell_Adapter
import com.example.temax.classes.CreateHouse
import com.example.temax.classes.House
import com.example.temax.services.HouseServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotStudentSell : AppCompatActivity() {
    //variavel para
    var sellOrRent: Int = 0
    var kindOfResidence: String = ""
    var elevatorResult: String = ""


    //link para os et
    private val spinner by lazy { findViewById<Spinner>(R.id.myspinner) }
    private val spinner2 by lazy { findViewById<Spinner>(R.id.SpinnerRentORSell) }

    private val etPrice by lazy { findViewById<EditText>(R.id.etPrice) }
    private val etTitle by lazy { findViewById<EditText>(R.id.etTitle) }
    private val etAddress by lazy { findViewById<EditText>(R.id.etAddress) }
    private val etConstruction_year by lazy { findViewById<EditText>(R.id.etConstruction_year) }
    private val etParking by lazy { findViewById<EditText>(R.id.etParking) }
    private val switchElevator by lazy { findViewById<Switch>(R.id.switchElevator) }
    private val etDescription by lazy { findViewById<EditText>(R.id.etDescription) }
    private val etPostal_code by lazy { findViewById<EditText>(R.id.etPostal_code) }


    private val etAvailableKitchen by lazy { findViewById<EditText>(R.id.EtAvailableKitchen) }
    private val etPrivateWc by lazy { findViewById<EditText>(R.id.EtPrivateWc) }
    private val etNumBeds by lazy { findViewById<EditText>(R.id.EtNumBeds) }
    private val etSharedRoom by lazy { findViewById<EditText>(R.id.EtSharedRoom) }

    private val etTotalLotArea by lazy { findViewById<EditText>(R.id.EtTotalLotArea) }
    private val etPrivateGrossArea by lazy { findViewById<EditText>(R.id.EtPrivateGrossArea) }
    private val etWcs by lazy { findViewById<EditText>(R.id.EtWcs) }
    private val etBedrooms by lazy { findViewById<EditText>(R.id.EtBedrooms) }

    // Lazy initialization for EditText in the "Apartment" section
    private val etFloor by lazy { findViewById<EditText>(R.id.EtFloor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_student_sell)

        //lista de items para os spinners
        val items = listOf(
            SpinnerItem("Apartement", R.mipmap.ic_flat),
            SpinnerItem("House", R.mipmap.ic_house),
            SpinnerItem("Room", R.mipmap.ic_room),
        )

        val items2 = listOf(
            SpinnerItem("Sell", R.mipmap.ic_buyhouse),
            SpinnerItem("Rent", R.mipmap.ic_rent),
        )

        val adapter2 = Spinner_Sell_Adapter(this, items2)
        spinner2.adapter = adapter2

        val adapter = Spinner_Sell_Adapter(this, items)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = items[position].text
                val selectedItem2 = items2[position].text

                //guarda se quer arrendar ou vender
                when (selectedItem2) {
                    "Sell" -> {
                        sellOrRent = 0
                    }

                    "Rent" -> {
                        sellOrRent = 1
                    }
                }

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
                    "Apartement" -> {
                        kindOfResidence = "Apartment"
                        // ... mostre outras EditTexts conforme necessário para "Apartment"
                        etFloor.visibility = View.VISIBLE
                        etWcs.visibility = View.VISIBLE
                        etBedrooms.visibility = View.VISIBLE

                    }

                    "House" -> {
                        kindOfResidence = "House"
                        // Mostrar as EditTexts necessárias para "Residence"
                        etTotalLotArea.visibility = View.VISIBLE
                        etPrivateGrossArea.visibility = View.VISIBLE
                        etWcs.visibility = View.VISIBLE
                        etBedrooms.visibility = View.VISIBLE
                    }

                    "Room" -> {
                        kindOfResidence = "Room"
                        etAvailableKitchen.visibility = View.VISIBLE
                        etPrivateWc.visibility = View.VISIBLE
                        etNumBeds.visibility = View.VISIBLE
                        etSharedRoom.visibility = View.VISIBLE
                    }
                }

                val switchElevator = findViewById<Switch>(R.id.switchElevator)
                switchElevator.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        // O switch está ligado (sim)
                        elevatorResult = "Sim"
                    } else {
                        // O switch está desligado (não)
                        elevatorResult = "Não"
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Implemente conforme necessário
            }
        }
    }

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
    }
    fun sellOrRentHouse(view: View) {
        when (kindOfResidence) {

            //se for House faz request para link diferente
            "House" -> {

                //para ver se vende ou arrenda
                var sellOrRentTemp: String = ""
                if (sellOrRent == 0) {
                    sellOrRentTemp = "Sell"
                } else {
                    sellOrRentTemp = "Rent"
                }
                Log.d("resposta","estas a chegar aqui?")

                //para ir buscar o token
                var userId = getSharedPreferences(this).getString("userId",null)!!
                val createHouseRequest = CreateHouse(
                    //TODO: por aqui o id do user guardado do login
                    UserID = userId.toInt(),
                    //transforma de string para double
                    Price = etPrice.text.toString().toDouble(),
                    Construction_year = etConstruction_year.text.toString().toInt(),
                    Parking = etParking.text.toString().toInt(),
                    //valor do switch adicionado na variavel temp em cima
                    Elevator = elevatorResult,
                    //default é 3
                    Prioraty_level = 3,
                    Description = etDescription.text.toString(),
                    Postal_code = etPostal_code.text.toString(),
                    Private_gross_area = etPrivateGrossArea.text.toString().toInt(),
                    Total_lot_area = etTotalLotArea.text.toString().toInt(),
                    Bedrooms = etBedrooms.text.toString().toInt(),
                    WCs = etWcs.text.toString().toInt(),
                    ListingType = sellOrRentTemp,
                    Title = etTitle.text.toString(),
                    Address = etAddress.text.toString()
                )
                requestCriarCasa(createHouseRequest)
            }

            "Apartement" ->{
                //TODO: criar apartamento e enviar para a base de dados
            }
        }

    }
}
    private fun requestCriarCasa(createHouseRequest: CreateHouse){
        val BASE_URL = "http://${BuildConfig.API_IP}:3000/house/createHouse/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(HouseServices::class.java)

        // Cria request com o createhouse object
        val call = service.createHouse(createHouseRequest)
        call.enqueue( object : Callback<CreateHouse> {
            override fun onResponse(call : Call<CreateHouse>, response: Response<CreateHouse>){
                if(response.code() == 200){
                    val retroFit2 = response.body()
                    Log.d("resposta",retroFit2.toString())
                    //TODO:meter aqui depois o intent para passar para a dashboard

                }
            }

            override fun onFailure(call: Call<CreateHouse>, t: Throwable) {
                print("error")
            }
        })

        //TODO: por aqui intent e um toast para depois de criar a casa ir para o dashboard
    }




