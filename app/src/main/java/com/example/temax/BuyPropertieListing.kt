package com.example.temax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.temax.adapters.AdapterListViewBuyProperties
import com.example.temax.classes.House
import com.example.temax.services.HouseServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BuyPropertieListing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_propertie_listing)

        val listView = findViewById<ListView>(R.id.listviewBuyProperties)


        val BASE_URL = "http://${BuildConfig.API_IP}:3000/house/" // Replace with your API base URL

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(HouseServices::class.java)
        val call = service.getAllHouses()
        call.enqueue( object : Callback<List<House>> {
            override fun onResponse(call : Call<List<House>>, response: Response<List<House>>){
                if(response.code() == 200){
                    val respondeBody = response.body()
                    val adapter = AdapterListViewBuyProperties(this@BuyPropertieListing,R.layout.custum_listview_annonces,respondeBody.orEmpty())
                    listView.adapter = adapter
                    Log.d("resposta",respondeBody.toString())
                }
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                print("error")
            }
        })

    }
}