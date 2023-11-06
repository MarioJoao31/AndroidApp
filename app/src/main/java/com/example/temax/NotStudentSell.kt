package com.example.temax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import com.example.temax.adapters.SpinnerItem
import com.example.temax.adapters.Spinner_Sell_Adapter

class NotStudentSell : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_student_sell)

        val spinner = findViewById<Spinner>(R.id.myspinner)
        val items = listOf(
            SpinnerItem("Apartment", R.mipmap.ic_flat),
            SpinnerItem("Residence", R.mipmap.ic_house),
            SpinnerItem("Room", R.mipmap.ic_room),
        )

        val adapter = Spinner_Sell_Adapter(this, items)
        spinner.adapter = adapter
    }
}