package com.example.temax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.temax.adapters.AdapterListViewBuyProperties
import com.example.temax.adapters.AdapterListViewRentProperties
import com.example.temax.classes.Apartement
import com.example.temax.classes.House
import com.example.temax.classes.Room
import com.example.temax.services.ApartementServices
import com.example.temax.services.HouseServices
import com.example.temax.services.RoomServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class BuyPropertieListing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_propertie_listing)

        val listView = findViewById<ListView>(R.id.listviewBuyProperties)


        val BASE_URL = "http://${BuildConfig.API_IP}:3000/house/sellHouses/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(HouseServices::class.java)

        val SECOND_URL = "http://${BuildConfig.API_IP}:3000/apartement/sellApartements/"

        val retrofit2: Retrofit = Retrofit.Builder()
            .baseUrl(SECOND_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service2 = retrofit2.create(ApartementServices::class.java)

        // URL para a função de quartos para venda
        val roomBaseUrl = "http://${BuildConfig.API_IP}:3000/room/sellRooms/"

        // Configuração do Retrofit para o roomService
        val roomRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(roomBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val roomService = roomRetrofit.create(RoomServices::class.java)

        // Execute the first API call
        val callHouses = service.getSellHouses()
        val callApartements = service2.getSellApartements()

        // Chamada do serviço getRentRooms proveniente do RoomServices
        val callRooms = roomService.getSellRooms()

        // Callback para a resposta do serviço getRentHouses()
        callHouses.enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                if (response.code() == 200) {
                    val houseList = response.body()

                    // Chamada do serviço getRentApartements
                    callApartements.enqueue(object : Callback<List<Apartement>> {
                        override fun onResponse(
                            call: Call<List<Apartement>>,
                            response: Response<List<Apartement>>
                        ) {
                            if (response.code() == 200) {
                                val apartementList = response.body()

                                // Chamada do serviço getRentRooms
                                callRooms.enqueue(object : Callback<List<Room>> {
                                    override fun onResponse(
                                        call: Call<List<Room>>,
                                        response: Response<List<Room>>
                                    ) {
                                        if (response.code() == 200) {
                                            val roomList = response.body()

                                            // Combina as listas de casas, apartamentos e quartos
                                            val combinedList = mutableListOf<Any>()
                                            combinedList.addAll(houseList.orEmpty())
                                            combinedList.addAll(apartementList.orEmpty())
                                            combinedList.addAll(roomList.orEmpty())

                                            // Ordene a lista combinada com base no nível de prioridade
                                            combinedList.sortBy {
                                                when (it) {

                                                    is House -> it.Prioraty_level
                                                    is Apartement -> it.Prioraty_level
                                                    is Room -> it.Prioraty_level

                                                    else -> 3 // Defina um valor padrão para outros tipos
                                                }
                                            }

                                            // Configuração do AdapterListViewRentProperties com a lista combinada
                                            val adapter =
                                                AdapterListViewRentProperties(
                                                    this@BuyPropertieListing,
                                                    R.layout.activity_student_rent_list,
                                                    combinedList
                                                )
                                            listView.adapter = adapter

                                            listView.setOnItemClickListener { _, _, position, _ ->
                                                val selectedItem = combinedList[position]
                                                val intent =
                                                    Intent(this@BuyPropertieListing, SelectedHouse::class.java)

                                                if (selectedItem is House || selectedItem is Apartement || selectedItem is Room) {

                                                    intent.putExtra("selectedItem", selectedItem as Serializable)
                                                    startActivity(intent)
                                                }
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                                        // Log de erro caso a chamada do RoomService falhe
                                        Log.e("StudentRentList", "Error fetching Rooms", t)
                                    }
                                })
                            }
                        }

                        override fun onFailure(call: Call<List<Apartement>>, t: Throwable) {
                            // Log de erro caso a chamada do ApartementService falhe
                            Log.e("StudentRentList", "Error fetching Apartements", t)
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                // Log de erro caso a chamada do HouseService falhe
                Log.e("StudentRentList", "Error fetching Houses", t)
            }
        })

    }
}