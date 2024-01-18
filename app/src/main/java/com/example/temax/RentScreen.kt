package com.example.temax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.temax.adapters.AdapterListViewRentProperties
import com.example.temax.classes.Apartement
import com.example.temax.classes.ApartementEntity
import com.example.temax.classes.House
import com.example.temax.classes.HouseEntity
import com.example.temax.classes.MyDatabase
import com.example.temax.classes.Room
import com.example.temax.classes.RoomEntity
import com.example.temax.services.ApartementServices
import com.example.temax.services.HouseServices
import com.example.temax.services.RoomServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

class RentScreen : AppCompatActivity() {

    private val combinedList = mutableListOf<Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_screen)

        val db = androidx.room.Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java, "temax.db"
        ).build()

        // Dentro da sua Activity ou Fragment
        val listView = findViewById<ListView>(R.id.listview_rent_announces)

        // URL para a função de casas para arrendar
        val houseBaseUrl = "http://${BuildConfig.API_IP}:3000/house/rentHouses/"

        // Configuração do Retrofit para o houseService
        val houseRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(houseBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val houseService = houseRetrofit.create(HouseServices::class.java)

        // URL para a função de apartamentos para arrendar
        val apartementBaseUrl = "http://${BuildConfig.API_IP}:3000/apartement/rentApartements/"

        // Configuração do Retrofit para o apartementService
        val apartementRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(apartementBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apartementService = apartementRetrofit.create(ApartementServices::class.java)

        // URL para a função de quartos para arrendar
        val roomBaseUrl = "http://${BuildConfig.API_IP}:3000/room/rentRooms/"

        // Configuração do Retrofit para o roomService
        val roomRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(roomBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val roomService = roomRetrofit.create(RoomServices::class.java)

        // Chamada do serviço getRentHouses proveniente do HouseServices
        val callHouses = houseService.getRentHouses()

        // Chamada do serviço getRentApartements proveniente do ApartementServices
        val callApartements = apartementService.getRentApartements()

        // Chamada do serviço getRentRooms proveniente do RoomServices
        val callRooms = roomService.getRentRooms()


        // Callback para a resposta do serviço getRentHouses()
        callHouses.enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                if (response.code() == 200) {
                    val houseList = response.body()

                    // Verifique se a lista não está vazia antes de inserir na base de dados "MyDatabase"
                    houseList?.let {
                        // Converte a lista de House para HouseEntity
                        val houseEntityList = it.map { house ->
                            HouseEntity(
                                houseID = house.HouseID,
                                userID = house.UserID,
                                price = house.Price,
                                constructionYear = house.Construction_year,
                                parking = house.Parking,
                                elevator = house.Elevator,
                                priorityLevel = house.Prioraty_level,
                                description = house.Description,
                                postalCode = house.Postal_code,
                                privateGrossArea = house.Private_gross_area,
                                totalLotArea = house.Total_lot_area,
                                bedrooms = house.Bedrooms,
                                wcs = house.WCs,
                                listingType = house.ListingType,
                                title = house.Title,
                                address = house.Address

                            )
                        }
                        // Inserir todas as casas na base de dados local "MyDatabase"
                        GlobalScope.launch(Dispatchers.IO) {

                            db.houseDao().deleteAllHouses()
                            Log.d("Sqlite" ,"Dados Antigos casas Removidos")

                            db.houseDao().insertAllHouses(houseEntityList)
                            Log.d("Sqlite" ,"Dados casas inseridos")
                        }
                    }

                    // Chamada do serviço getRentApartements
                    callApartements.enqueue(object : Callback<List<Apartement>> {
                        override fun onResponse(
                            call: Call<List<Apartement>>,
                            response: Response<List<Apartement>>
                        ) {
                            if (response.code() == 200) {
                                val apartementList = response.body()

                                // Verifica se a lista não está vazia antes de inserir na base de dados
                                apartementList?.let {

                                    // Converte a lista de apartement para ApartementEntity

                                    val apartementEntityList = it.map { apartement ->
                                        ApartementEntity (
                                            ApartementID = apartement.ApartementID,
                                            UserID = apartement.UserID,
                                            Price = apartement.Price,
                                            Construction_year = apartement.Construction_year,
                                            Parking = apartement.Parking,
                                            Elevator = apartement.Elevator,
                                            Prioraty_level = apartement.Prioraty_level,
                                            Description = apartement.Description,
                                            Postal_code = apartement.Postal_code,
                                            Floor = apartement.Floor,
                                            Bedrooms = apartement.Bedrooms,
                                            WCs = apartement.WCs,
                                            ListingType = apartement.ListingType,
                                            Title = apartement.Title,
                                            Address = apartement.Address
                                        )
                                    }

                                    // Inserir todos os apartamentos na base de dados local "MyDatabase"
                                    GlobalScope.launch(Dispatchers.IO) {

                                        db.apartementDao().deleteAllApartements()
                                        Log.d("Sqlite" ,"Dados Antigos apartamentos Removidos")

                                        db.apartementDao().insertAllApartements(apartementEntityList)
                                        Log.d("Sqlite" ,"Dados apartamentos inseridos")
                                    }
                                }

                                // Chamada do serviço getRentRooms
                                callRooms.enqueue(object : Callback<List<Room>> {
                                    override fun onResponse(
                                        call: Call<List<Room>>,
                                        response: Response<List<Room>>
                                    ) {
                                        if (response.code() == 200) {
                                            val roomList = response.body()

                                            // Verifica se a lista não está vazia antes de inserir na base de dados
                                            roomList?.let {

                                                // Converte a lista de apartement para ApartementEntity
                                                val roomEntityList = it.map { room ->
                                                    RoomEntity(
                                                        RoomID = room.RoomID,
                                                        UserID = room.UserID,
                                                        Price = room.Price,
                                                        Construction_year = room.Construction_year,
                                                        Parking = room.Parking,
                                                        Elevator = room.Elevator,
                                                        Prioraty_level = room.Prioraty_level,
                                                        Description = room.Description,
                                                        Postal_code = room.Postal_code,
                                                        Num_beds = room.Num_beds,
                                                        Private_wc = room.Private_wc,
                                                        Available_kitchen = room.Available_kitchen,
                                                        ListingType = room.ListingType,
                                                        Shared_room = room.Shared_room,
                                                        Title = room.Title,
                                                        Address = room.Address
                                                    )
                                                }

                                                GlobalScope.launch(Dispatchers.IO) {

                                                    db.roomDao().deleteAllRooms()
                                                    Log.d("Sqlite" ,"Dados Antigos quartos Removidos")

                                                    db.roomDao().insertAllRooms(roomEntityList)
                                                    Log.d("Sqlite" ,"Dados quartos inseridos")
                                                }
                                            }

                                            // Combina as listas de casas, apartamentos e quartos
                                            val combinedList = mutableListOf<Any>()
                                            combinedList.addAll(houseList.orEmpty())
                                            combinedList.addAll(apartementList.orEmpty())
                                            combinedList.addAll(roomList.orEmpty())

                                            // Ordene a lista combinada com base no nível de prioridade
                                            combinedList.sortBy {
                                                when (it) {

                                                    is House -> it.Prioraty_level
                                                    is Apartement -> it.Prioraty_level
                                                    is Room -> it.Prioraty_level

                                                    else -> 3 // Defina um valor padrão para outros tipos
                                                }
                                            }

                                            // Configuração do AdapterListViewRentProperties com a lista combinada
                                            val adapter =
                                                AdapterListViewRentProperties(
                                                    this@RentScreen,
                                                    R.layout.activity_rent_screen,
                                                    combinedList
                                                )
                                            listView.adapter = adapter

                                            listView.setOnItemClickListener { _, _, position, _ ->
                                                val selectedItem = combinedList[position]
                                                val intent =
                                                    Intent(this@RentScreen, SelectedHouse::class.java)

                                                if (selectedItem is House || selectedItem is Apartement || selectedItem is Room) {

                                                    intent.putExtra("selectedItem", selectedItem as Serializable)
                                                    startActivity(intent)
                                                }
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                                        // Log de erro caso a chamada do RoomService falhe
                                        Log.e("StudentRentList", "Error fetching Rooms", t)
                                    }
                                })
                            }
                        }

                        override fun onFailure(call: Call<List<Apartement>>, t: Throwable) {
                            // Log de erro caso a chamada do ApartementService falhe
                            Log.e("StudentRentList", "Error fetching Apartements", t)
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                // Log de erro caso a chamada do HouseService falhe logo tenho de meter o sql lite aqui.
                Log.e("SQLLITE", "Error conect to the API", t)

                // Se a chamada não for bem-sucedida, recupero aqui  os dados da base de dados local "MyDatabase"
                GlobalScope.launch(Dispatchers.IO) {

                    val localHouseList = db.houseDao().getHousesForRent()
                    val localApartementList = db.apartementDao().getApartementsForRent()
                    val localRoomList = db.roomDao().getRooms()

                    // Combina  as listas das casas e apartamentos da base de dados local "MyDatabase"
                    val combinedList = mutableListOf<Any>()
                    combinedList.addAll(localHouseList)
                    combinedList.addAll(localApartementList)
                    combinedList.addAll(localRoomList)

                    // Ordene a lista combinada com base no nível de prioridade
                    combinedList.sortBy {
                        when (it) {

                            is HouseEntity -> it.priorityLevel
                            is ApartementEntity -> it.Prioraty_level
                            is RoomEntity -> it.Prioraty_level


                            else -> 3 // Defina um valor padrão para outros tipos
                        }
                    }

                    Log.d("Sqlite", "Dados armazenados das casas apartamentos e quartos no sqlLite com sucesso.")

                    withContext(Dispatchers.Main) {
                        // Exibe os dados na ListView
                        displayDataInListView(combinedList)
                    }
                }
            }
        })

        //TODO: Tem que levar o ID da casa, com o intent de put extra
        listView.setOnItemClickListener { _, _, position, _ ->
            // Iniciar a atividade SelectedHouse e passar os detalhes da casa selecionada através de Intent extras
            val intent = Intent(this@RentScreen, SelectedHouse::class.java)
            startActivity(intent)
        }
    }

    private fun displayDataInListView(dataList: List<Any>) {

        // Encontra a ListView no layout da activity
        val listView = findViewById<ListView>(R.id.listview_rent_announces)

        // Configuração do AdapterListViewRentProperties com a lista do banco de dados local
        val adapter = AdapterListViewRentProperties(
            this@RentScreen,
            R.layout.activity_rent_screen,
            dataList
        )
        listView.adapter = adapter

        /* Caso precise vir aqui.
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = dataList[position]
            val intent = Intent(this@StudentRentList, SelectedHouse::class.java)

            if (selectedItem is HouseEntity) {
                intent.putExtra("selectedItem", selectedItem as Serializable)
                startActivity(intent)
            }
        } */
    }
}
