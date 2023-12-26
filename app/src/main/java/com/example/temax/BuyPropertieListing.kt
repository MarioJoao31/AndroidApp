package com.example.temax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.temax.adapters.AdapterListViewBuyProperties
import com.example.temax.classes.Apartement
import com.example.temax.classes.House
import com.example.temax.classes.Room
import com.example.temax.services.ApartementServices
import com.example.temax.services.HouseServices
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

        // Execute the first API call
        val call1 = service.getSellHouses()
        val call2 = service2.getSellApartements()

        call1.enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                if (response.code() == 200) {
                    val houseList = response.body()

                    // Make the second API call
                    call2.enqueue(object : Callback<List<Apartement>> {
                        override fun onResponse(call: Call<List<Apartement>>, response: Response<List<Apartement>>) {
                            if (response.code() == 200) {
                                val apartementList = response.body()
                                // Combine the results of both calls
                                val combinedList = (houseList.orEmpty() + apartementList.orEmpty()).toMutableList()

                                // Create and set the adapter with the combined list
                                val adapter = AdapterListViewBuyProperties(
                                    this@BuyPropertieListing,
                                    R.layout.custum_listview_annonces,
                                    combinedList
                                )
                                listView.adapter = adapter

                                listView.setOnItemClickListener { _, _, position, _ ->
                                    val selectedItem = combinedList[position]
                                    val intent = Intent(this@BuyPropertieListing, SelectedHouse::class.java)


                                    if (selectedItem is House || selectedItem is Apartement || selectedItem is Room) {
                                        intent.putExtra("selectedItem", selectedItem as Serializable)
                                        startActivity(intent)
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<List<Apartement>>, t: Throwable) {
                            // Log de erro caso a chamada do ApartementService falhe
                            Log.e("StudentSellList", "Error fetching Apartements", t)
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                print("error")
            }
        })

    }
}