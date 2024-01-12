package com.example.temax

import AdapterListViewPayments
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.temax.classes.Payment
import com.example.temax.services.PaymentServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ListPayments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_payments)

        // Encontra a ListView no layout da activity
        val listView = findViewById<ListView>(R.id.listViewPayments)

        // Obtem o userID do SharedPreferences
        val userID = getSharedPreferences("Temax", Context.MODE_PRIVATE)
            .getString("userId", null)?.toIntOrNull() ?: -1 // -1 é um valor padrão

        // URL para a função de pagamentos efectuados
        val paymentBaseUrl = "http://${BuildConfig.API_IP}:3000/payment/$userID/"

        // Configuração do Retrofit para o serviço de pagamentos (payments)
        val paymentRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(paymentBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val paymentService = paymentRetrofit.create(PaymentServices::class.java)

        // Chama a API para obter os pagamentos do usuário
        val callPayments = paymentService.getUserPayPayment(userID)

        callPayments.enqueue(object : Callback<List<Payment>> {
            override fun onResponse(call: Call<List<Payment>>, response: Response<List<Payment>>) {
                if (response.isSuccessful) {
                    val payments = response.body()

                    // Log dos dados recebidos
                    Log.d("ListPayments", "Pagamentos recebidos: $payments")

                    // Crie um Adapter e configure-o na sua ListView
                    val adapter = AdapterListViewPayments(this@ListPayments, R.layout.activity_list_payments, payments!!)
                    listView.adapter = adapter

                } else {
                    // Trata o caso em que a resposta não foi bem-sucedida
                      Log.e("ListPayments", "Erro na resposta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Payment>>, t: Throwable) {

                 Log.e("ListPayments", "Erro na chamada: ${t.message}")
            }
        })


    }
}