package com.example.temax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class NotStudentSellOrRentOrBuy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_student_sell_or_rent_or_buy)
    }

    fun GoToSellPage (view: View){
        val intent = Intent(this@NotStudentSellOrRentOrBuy, NotStudentSell::class.java)
        startActivityForResult(intent,1)
    }

    fun GoToRentPage (view: View){
        val intent = Intent(this@NotStudentSellOrRentOrBuy, RentScreen::class.java)
        startActivityForResult(intent,1)
    }
}