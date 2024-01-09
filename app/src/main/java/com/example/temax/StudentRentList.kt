package com.example.temax

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
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
import retrofit2.create
import java.io.Serializable

class StudentRentList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_rent_list)

        // Encontra a ListView no layout da activity
        val listView = findViewById<ListView>(R.id.listview_student_rent_announces)

        // URL para a função de casas para arrendar
        val houseBaseUrl = "http://${BuildConfig.API_IP}:3000/house/rentHouses/"

        // Configuração do Retrofit para o houseService
        val houseRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(houseBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val houseService = houseRetrofit.create(HouseServices::class.java)

        // URL para a função de apartamentos para arrendar
        val apartementBaseUrl = "http://${BuildConfig.API_IP}:3000/apartement/rentApartements/"

        // Configuração do Retrofit para o apartementService
        val apartementRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(apartementBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apartementService = apartementRetrofit.create(ApartementServices::class.java)

        // URL para a função de quartos para arrendar
        val roomBaseUrl = "http://${BuildConfig.API_IP}:3000//room/rentRooms/"

        // Configuração do Retrofit para o roomService
        val roomRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(roomBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val roomService = roomRetrofit.create(RoomServices::class.java)

        // Chamada do serviço getRentHouses proveniente do HouseServices
        val callHouses = houseService.getRentHouses()

        // Chamada do serviço getRentApartements proveniente do ApartementServices
        val callApartements = apartementService.getRentApartements()

        // Chamada do serviço getRentRooms proveniente do RoomServices
        val callRooms = roomService.getRentRooms()


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
                                                    this@StudentRentList,
                                                    R.layout.activity_student_rent_list,
                                                    combinedList
                                                )
                                            listView.adapter = adapter

                                            listView.setOnItemClickListener { _, _, position, _ ->
                                                val selectedItem = combinedList[position]
                                                val intent =
                                                    Intent(
                                                        this@StudentRentList,
                                                        SelectedHouse::class.java
                                                    )

                                                if (selectedItem is House || selectedItem is Apartement || selectedItem is Room) {
                                                    intent.putExtra(
                                                        "selectedItem",
                                                        selectedItem as Serializable
                                                    )
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

        //TODO: Tem que levar o ID da casa, com o intent de put extra
        listView.setOnItemClickListener { _, _, position, _ ->
            // Iniciar a atividade SelectedHouse e passar os detalhes da casa selecionada através de Intent extras
            val intent = Intent(this@StudentRentList, SelectedHouse::class.java)
            startActivity(intent)
        }
    }
}