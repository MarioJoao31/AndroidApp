package com.example.temax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.temax.adapters.AdapterListViewBuyProperties
import com.example.temax.classes.Apartement
import com.example.temax.classes.House
import com.example.temax.services.ApartementServices
import com.example.temax.services.HouseServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class StudentRentList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_rent_list)

        val listView = findViewById<ListView>(R.id.listview_student_rent_announces)

        //URL para a função de casas para arrendar /
        val BASE_URL = "http://172.16.15.158:3000/house/rentHouses/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(HouseServices::class.java)

        //chamada do serviço getRentHouses proveniente do HouseServices
        val call = service.getRentHouses()
        call.enqueue(object : Callback<List<House>>{
            override fun onResponse(call : Call<List<House>>, response: Response<List<House>>){
                if(response.code() == 200){
                    val respondeBody = response.body()
                    val adapter = AdapterListViewBuyProperties(this@StudentRentList,R.layout.activity_student_rent_list,respondeBody.orEmpty())
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