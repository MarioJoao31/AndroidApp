package com.example.temax

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.temax.adapters.AdapterSpinnerServiceTop
import com.example.temax.adapters.SpinnerResidenceServiceTop
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

class ServiceTop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_top)

        // Encontra o spinner no layout da activity
        val spinner = findViewById<Spinner>(R.id.spinnerSelectResidence)

        // Obtem o userID do SharedPreferences
        val userID = getSharedPreferences("Temax", Context.MODE_PRIVATE)
            .getString("userId", null)?.toIntOrNull() ?: -1 // -1 é um valor padrão

        //URLs com base no userID
        val houseBaseUrl = "http://${BuildConfig.API_IP}:3000/house/$userID/"
        val apartementBaseUrl = "http://${BuildConfig.API_IP}:3000/apartement/$userID/"
        val roomBaseUrl = "http://${BuildConfig.API_IP}:3000/room/$userID/"

        // Configuração do Retrofit para o serviço de casas (houses)
        val houseRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(houseBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val houseService = houseRetrofit.create(HouseServices::class.java)

        val apartementRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(apartementBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apartementService = apartementRetrofit.create(ApartementServices::class.java)

        val roomRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(roomBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val roomService = roomRetrofit.create(RoomServices::class.java)



        // Chamada do serviço getUserHouses proveniente do HouseServices
        val callHouses = houseService.getUserHouses(userID)

        // Chamada do serviço getUserApartments proveniente do ApartementServices
        val callApartements = apartementService.getUserApartments(userID)

        // Chamada do serviço getUserRooms proveniente do RoomServices
        val callRooms = roomService.getUserRooms(userID)

        // Callback para a resposta do serviço getRentHouses()
        // Callback para a resposta do serviço getRentHouses()
        callHouses.enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                if (response.code() == 200) {
                    val houseList = response.body()

                    // Callback para a resposta do serviço getRentApartements
                    callApartements.enqueue(object : Callback<List<Apartement>> {
                        override fun onResponse(
                            call: Call<List<Apartement>>,
                            response: Response<List<Apartement>>
                        ) {
                            if (response.code() == 200) {
                                val apartementList = response.body()

                                // Chamada do serviço getRentRooms proveniente do RoomServices
                                val callRooms = roomService.getUserRooms(userID)

                                // Callback para a resposta do serviço getRentRooms()
                                callRooms.enqueue(object : Callback<List<Room>> {
                                    override fun onResponse(
                                        call: Call<List<Room>>,
                                        response: Response<List<Room>>
                                    ) {
                                        if (response.code() == 200) {
                                            val roomList = response.body()

                                            // Combine as listas de casas, apartamentos e quartos
                                            val combinedList = mutableListOf<Any>()
                                            combinedList.addAll(houseList.orEmpty())
                                            combinedList.addAll(apartementList.orEmpty())
                                            combinedList.addAll(roomList.orEmpty())

                                            // Configuração do AdapterSpinnerServiceTop com a lista combinada
                                            val adapter = AdapterSpinnerServiceTop(
                                                this@ServiceTop,
                                                combinedList.map { item ->
                                                    SpinnerResidenceServiceTop(
                                                        when (item) {
                                                            is House -> item.Title ?: ""
                                                            is Apartement -> item.Title ?: ""
                                                            is Room -> item.Title ?: ""
                                                            // Adicione outros tipos, se necessário
                                                            else -> ""
                                                        },
                                                        R.mipmap.house
                                                    )
                                                }
                                            )

                                            spinner.adapter = adapter
                                        }
                                    }

                                    override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                                        // Log de erro caso a chamada do RoomService falhe
                                        Log.e("ServiceTop", "Error fetching Rooms", t)
                                    }
                                })
                            }
                        }

                        override fun onFailure(
                            call: Call<List<Apartement>>,
                            t: Throwable
                        ) {
                            // Log de erro caso a chamada do ApartementService falhe
                            Log.e("ServiceTop", "Error fetching Apartements", t)
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                // Log de erro caso a chamada do HouseService falhe
                Log.e("ServiceTop", "Error fetching Houses", t)
            }
        })

    }
}
