package com.example.temax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import com.example.temax.adapters.AdapterSpinnerServiceTop
import com.example.temax.adapters.SpinnerResidenceServiceTop

class ServiceTop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_top)

        // Supondo que você tenha uma lista de itens para o Spinner
        val spinnerItems = listOf(
            SpinnerResidenceServiceTop("Casa 1", R.mipmap.house),
            SpinnerResidenceServiceTop("Casa 2", R.mipmap.house),
            // Adicione mais itens conforme necessário
        )

        val adapter = AdapterSpinnerServiceTop(this, spinnerItems)

        val spinner = findViewById<Spinner>(R.id.spinnerSelectResidence)
        spinner.adapter = adapter
    }
}