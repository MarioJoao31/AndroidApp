package com.example.temax

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.temax.adapters.AdapterSpinnerServiceTop
import com.example.temax.adapters.SpinnerItem
import com.example.temax.adapters.SpinnerResidenceServiceTop
import com.example.temax.adapters.Spinner_Sell_Adapter
import com.example.temax.classes.Apartement
import com.example.temax.classes.CreatePayment
import com.example.temax.classes.House
import com.example.temax.classes.Room
import com.example.temax.services.ApartementServices
import com.example.temax.services.HouseServices
import com.example.temax.services.PaymentServices
import com.example.temax.services.RoomServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ServiceTop : AppCompatActivity() {

    private val spinnerTypePayment by lazy { findViewById<Spinner>(R.id.spinnerSelectTypePayment) }
    private var selectedTypePayment: String = ""
    private val switchPrice by lazy { findViewById<Switch>(R.id.SwitchPrice) }
    private var isSwitchPriceChecked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_top)

        val spinnerPayment = listOf(
            SpinnerItem("MB WAY", R.mipmap.mbway),
            SpinnerItem("PayPal", R.mipmap.paypal),
            SpinnerItem("Multibanco", R.mipmap.multibanco)
            // Adicione mais itens conforme necessário
        )

        //Reutilizei um Adapter, do sell, porque chegava para o que queria.
        val paymentAdapter  = Spinner_Sell_Adapter(this, spinnerPayment)
        spinnerTypePayment.adapter = paymentAdapter

        // Define um listener para o spinnerTypePayment
        spinnerTypePayment.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Obtém o tipo de pagamento selecionado
                selectedTypePayment = (spinnerTypePayment.selectedItem as SpinnerItem).text

                //para ver se está a funcionar
                Log.d("ServiceTop", "Tipo de pagamento selecionado: $selectedTypePayment")
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Trata a situação em que nada é selecionado (se necessário)
            }
        })

        // Encontra o switch no layout da activity
        switchPrice.setOnCheckedChangeListener { _, isChecked ->
            // Atualiza o estado do switch
            isSwitchPriceChecked = isChecked
            // Verifica se o switch está ativado e define o preço conforme necessário
            val price = if (isSwitchPriceChecked) 15.0 else 0.0
            Log.d("ServiceTop", "Preço do pagamento: $price")
        }

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

    fun executePayment(view: View) {

        val context = this // Salvar a referência ao contexto da atividade

        // Obtem o userID do SharedPreferences
        val userID = getSharedPreferences("Temax", Context.MODE_PRIVATE)
            .getString("userId", null)?.toIntOrNull() ?: -1 // -1 é um valor padrão

        val price = if (isSwitchPriceChecked) 15.0 else 0.0

        if (isSwitchPriceChecked || price > 0) {
            val currentTime = getCurrentDateTime()

            val createPaymentRequest = CreatePayment(
                UserID = userID.toInt(),
                Price = price,
                Status = "Pago",
                Type_Payment = selectedTypePayment,
                Date = currentTime,
            )

            // Chama a função para criar o pagamento com os dados fornecidos
            requestCriarPayment(createPaymentRequest)

            // Chama a função para atualizar o Prioraty_level das casas
            requestUpdatePrioratyLevelHouses(context)

            // Chama a função para atualizar o Prioraty_level dos apartamentos
            requestUpdatePrioratyLevelApartements(context)

            // Chama a função para atualizar o Prioraty_level dos quartos
            requestUpdatePrioratyLevelRooms(context)

            showToast(context, "Pagamento criado com sucesso")
            finish()
        } else {
            showToast(context, "Confirme o pagamento de 15 € ")
        }
    }
}

private fun requestCriarPayment(createPaymentRequest: CreatePayment){

    val BASE_URL = "http://${BuildConfig.API_IP}:3000/payment/createPayment/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(PaymentServices::class.java)

    //Cria o request com o createPayment object
    val call = service.createPayment(createPaymentRequest)

    call.enqueue(object : Callback<CreatePayment> {

        override fun onResponse(call: Call<CreatePayment>, response: Response<CreatePayment>) {

            if (response.code() == 200){
                val retroFit2 = response.body()
                Log.d("request payment", retroFit2.toString())
            }
        }

        override fun onFailure(call: Call<CreatePayment>, t: Throwable) {
            print("error")
        }
    })
}

private fun requestUpdatePrioratyLevelHouses(context: Context) {

    // Obtem o userID do SharedPreferences
    val userID = context.getSharedPreferences("Temax", Context.MODE_PRIVATE)
        .getString("userId", null)?.toIntOrNull() ?: -1 // -1 é um valor padrão

    val BASE_URL = "http://${BuildConfig.API_IP}:3000/house/changePrioraty/$userID/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(HouseServices::class.java)

    // Cria o request para a atualização do Prioraty_level
    val call = service.updateHousePrioratyLevel(userID)

    call.enqueue(object : Callback<List<House>> {

        override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
            if (response.code() == 200) {
                Log.d("updatePrioratyLevel", "Prioraty_level das casas atualizado com sucesso")
            }
        }

        override fun onFailure(call: Call<List<House>>, t: Throwable) {
            print("error das casas")
        }
    })
}

private fun requestUpdatePrioratyLevelApartements(context: Context) {

    // Obtem o userID do SharedPreferences
    val userID = context.getSharedPreferences("Temax", Context.MODE_PRIVATE)
        .getString("userId", null)?.toIntOrNull() ?: -1 // -1 é um valor padrão

    val BASE_URL = "http://${BuildConfig.API_IP}:3000/apartement/changePrioraty/$userID/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ApartementServices::class.java)

    // Cria o request para a atualização do Prioraty_level
    val call = service.updateApartementPrioratyLevel(userID)

    call.enqueue(object : Callback<List<Apartement>> {

        override fun onResponse(call: Call<List<Apartement>>, response: Response<List<Apartement>>) {
            if (response.code() ==200) {
                Log.d("updatePrioratyLevel", "Prioraty_level dos apartamentos atualizado com sucesso")
            }
        }

        override fun onFailure(call: Call<List<Apartement>>, t: Throwable) {
            print("error dos apartamentos")
        }
    })

}

private fun requestUpdatePrioratyLevelRooms(context: Context){

    // Obtem o userID do SharedPreferences
    val userID = context.getSharedPreferences("Temax", Context.MODE_PRIVATE)
        .getString("userId", null)?.toIntOrNull() ?: -1 // -1 é um valor padrão

    val BASE_URL = "http://${BuildConfig.API_IP}:3000/room/changePrioraty/$userID/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(RoomServices::class.java)

    // Cria o request para a atualização do Prioraty_level
    val call = service.updateRoomPrioratyLevel(userID)

    call.enqueue(object : Callback<List<Room>>{
        override fun onResponse(call: Call<List<Room>>, response: Response<List<Room>>) {
            if (response.code() == 200) {
                Log.d("updatePrioratyLevel", "Prioraty Level dos quartos atualizado com sucesso")
            }
        }
        override fun onFailure(call: Call<List<Room>>, t: Throwable) {
            print("error dos quartos")
        }
    })
}


private fun getCurrentDateTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentDate = Date()
    return sdf.format(currentDate) // Retorna a data e hora formatada
}
private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
