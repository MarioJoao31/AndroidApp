package com.example.temax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SelectTypeUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_type_user)
    }

    fun SelectBtTypeStudent (view:View){
        val intent = Intent(this@SelectTypeUser, StudentRentList::class.java)
        startActivityForResult(intent,1)
    }

    fun SelectBtTypeNotStudent (view: View){
        val intent = Intent(this@SelectTypeUser, NotStudentSellOrRentOrBuy::class.java)
        startActivityForResult(intent,1)
    }

    fun GoToDashboardUser(view: View) {
        val intent = Intent(this@SelectTypeUser, DashboardUser::class.java)
        startActivityForResult(intent,1)
    }
}