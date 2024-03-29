package com.example.temax

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.temax.classes.Apartement
import com.example.temax.classes.House
import com.example.temax.classes.Room
import com.example.temax.manager.Message
import com.example.temax.manager.SocketManager
import kotlinx.coroutines.selects.select
import org.json.JSONObject
import java.io.Serializable

class SelectedHouse : AppCompatActivity(), SocketManager.MessageListener {

    private var houseID: Int ?= 0 // Variável de classe para armazenar houseID
    private var apartementID: Int ?= 0
    private var roomID: Int ?= 0

    private var userID2 = 0

    private val socketManager = SocketManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_house)

        //conenct em todas as activites
        socketManager.connectSocket()


        val selectedItem = intent.getSerializableExtra("selectedItem") as? Serializable

        //criar as variaveis
        var price: Double ? = 0.0
        var description: String ? = ""
        var wcs: Int ? = 0
        var postal_code : String ?= ""
        var construction_year: Int ? = 0
        var parking: Int ? = 0
        var elevator: String ? = ""
        var private_gross_area: Int ? = 0
        var total_lot_area: Int ? = 0
        var bedrooms:Int ? = 0
        var tittle: String ? = ""
        var address: String ? = ""
        var floor: Int ?= 0
        var available_kitchen: String ? = ""
        var private_wc: Int ?= 0
        var num_beds : Int ?= 0
        var shared_room : String ?= ""

        //atribui os valores
        if (selectedItem != null) {
            if (selectedItem is House) {
                userID2 = selectedItem.UserID
                price = selectedItem.Price
                description = selectedItem.Description
                wcs = selectedItem.WCs
                postal_code = selectedItem.Postal_code
                construction_year = selectedItem.Construction_year
                parking = selectedItem.Parking
                elevator = selectedItem.Elevator
                private_gross_area = selectedItem.Private_gross_area
                total_lot_area = selectedItem.Total_lot_area
                bedrooms = selectedItem.Bedrooms
                tittle = selectedItem.Title
                address = selectedItem.Address
                houseID = selectedItem.HouseID
            }
            if (selectedItem is Apartement) {
                userID2 = selectedItem.UserID
                price = selectedItem.Price
                description = selectedItem.Description
                wcs = selectedItem.WCs
                postal_code = selectedItem.Postal_code
                construction_year = selectedItem.Construction_year
                parking = selectedItem.Parking
                elevator = selectedItem.Elevator
                bedrooms = selectedItem.Bedrooms
                tittle = selectedItem.Title
                address = selectedItem.Address
                floor = selectedItem.Floor
                apartementID = selectedItem.ApartementID

            }
            if (selectedItem is Room) {
                userID2 = selectedItem.UserID
                price = selectedItem.Price
                description = selectedItem.Description
                postal_code = selectedItem.Postal_code
                construction_year = selectedItem.Construction_year
                parking = selectedItem.Parking
                elevator = selectedItem.Elevator
                tittle = selectedItem.Title
                address = selectedItem.Address
                available_kitchen = selectedItem.Available_kitchen
                private_wc = selectedItem.Private_wc
                num_beds = selectedItem.Num_beds
                shared_room = selectedItem.Shared_room
                roomID = selectedItem.RoomID
            }
        }
        // Encontre os TextViews na sua UI
        val textViewPrice = findViewById<TextView>(R.id.textViewPrice)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val text_WCs = findViewById<TextView>(R.id.textViewWCs)
        val textViewPostalCode = findViewById<TextView>(R.id.textViewPostalCode)
        val textViewConstructionYear = findViewById<TextView>(R.id.textViewConstructionYear)
        val textViewParking = findViewById<TextView>(R.id.textViewParking)
        val textViewElevator = findViewById<TextView>(R.id.textViewElevator)
        val textViewPrivateGrossArea = findViewById<TextView>(R.id.textViewPrivateGrossArea)
        val textViewTotalLotArea = findViewById<TextView>(R.id.textViewTotalLotArea)
        val textViewBedrooms = findViewById<TextView>(R.id.textViewBedrooms)
        val textViewTittle = findViewById<TextView>(R.id.textViewTittle)
        val textViewAddress = findViewById<TextView>(R.id.textViewAddress)
        val textViewFloors = findViewById<TextView>(R.id.textViewFloors)
        val textViewSharedRoom = findViewById<TextView>(R.id.textViewSharedRoom)
        val textViewPrivatewcs = findViewById<TextView>(R.id.textViewPrivatewcs)
        val textViewNumBeds = findViewById<TextView>(R.id.textViewNumBeds)
        val textViewAvailableKitchen = findViewById<TextView>(R.id.textViewAvailableKitchen)


        // Encontre outros TextViews conforme necessário

        //////////////////////////////////
        ////////// Visibilidade do screen
        //////////////////////////////////

        // Configure os valores nos TextViews
        if (price != null) {
            textViewPrice.text = "$price"
        } else {
            textViewPrice.visibility = View.GONE
        }
        if (description != null) {
            textViewDescription.text = "$description"
        } else {
            textViewDescription.visibility = View.GONE
        }
        if (wcs != null) {
            text_WCs.text = "$wcs"
        } else {
            text_WCs.visibility = View.GONE
        }
        if (postal_code != null) {
            textViewPostalCode.text ="$postal_code"
        } else {
            textViewPostalCode.visibility = View.GONE
        }
        if (construction_year != null) {
            textViewConstructionYear.text = "$construction_year"
        } else {
            textViewConstructionYear.visibility = View.GONE
        }
        if (parking != null) {
            textViewParking.text = "$parking"
        } else {
            textViewParking.visibility = View.GONE
        }
        if (elevator != null) {
            textViewElevator.text = "$elevator"
        } else {
            textViewElevator.visibility = View.GONE
        }
        if (private_gross_area != null) {
            textViewPrivateGrossArea.text = "$private_gross_area"
        } else {
            textViewPrivateGrossArea.visibility = View.GONE
        }
        if (total_lot_area != null) {
            textViewTotalLotArea.text = "$total_lot_area"
        } else {
            textViewTotalLotArea.visibility = View.GONE
        }
        if (bedrooms != null) {
            textViewBedrooms.text = "$bedrooms"
        } else {
            textViewBedrooms.visibility = View.GONE
        }
        if (tittle != null) {
            textViewTittle.text = "$tittle"
        } else {
            textViewTittle.visibility = View.GONE
        }
        if (address != null) {
            textViewAddress.text = "$address"
        } else {
            textViewAddress.visibility = View.GONE
        }
        if (floor != 0) {
            textViewFloors.text = "$floor"
        } else {
            textViewFloors.visibility = View.GONE
        }
        if (shared_room != null) {
            textViewSharedRoom.text = "$shared_room"
        } else {
            textViewSharedRoom.visibility = View.GONE
        }
        if (available_kitchen != "") {
            textViewAvailableKitchen.text = "$available_kitchen"
        } else {
            textViewAvailableKitchen.visibility = View.GONE
        }
        if (private_wc != 0) {
            textViewPrivatewcs.text = "$private_wc"
        } else {
            textViewPrivatewcs.visibility = View.GONE
        }
        if (num_beds != 0) {
            textViewNumBeds.text = "$num_beds"
        } else {
            textViewNumBeds.visibility = View.GONE
        }
        // Configure outros TextViews conforme necessário

    }

    fun GoToComments (view: View){
        val intent = Intent(this@SelectedHouse, CommentsScreen::class.java)
        intent.putExtra("houseID", houseID)
        intent.putExtra("apartementID", apartementID)
        intent.putExtra("roomID", roomID)
        startActivity(intent)
    }


    fun GoToChat (view: View){

        val userID = getSharedPreferences("Temax", Context.MODE_PRIVATE)
            .getString("userId", null)?.toIntOrNull() ?: -1 // -1 é um valor padrão


        val data = JSONObject().apply {
            put("userID1", userID.toString()).put("userID2",userID2.toString())
        }

        //criar channel
        socketManager.sendRequest5(data)

        val intent = Intent(this@SelectedHouse, ChatChannels::class.java)
        startActivityForResult(intent,1)

    }

    override fun onNewMessage(message: Message) {
        TODO("Not yet implemented")
    }

    override fun userChannelsListener(data: String) {
        TODO("Not yet implemented")
    }

    override fun getChannelMessagesListener(messages: List<Message>) {
        TODO("Not yet implemented")
    }

    override fun onOtherEvent(data: String) {
        TODO("Not yet implemented")
        Log.d("socketAPI",data)
    }
}