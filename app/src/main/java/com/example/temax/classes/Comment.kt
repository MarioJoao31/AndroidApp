import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Timestamp

data class Comment(
    @SerializedName("ComentID") val commentID: Int,
    @SerializedName("UserID") val userID: Int,
    @SerializedName("Coment_Text") val commentText: String,
    @SerializedName("Coment_Datetime") val commentDatetime: Timestamp,
    @SerializedName("RoomID") val roomID: Int,
    @SerializedName("HouseID") val houseID: Int,
    @SerializedName("ApartmentID") val apartmentID: Int
) : Serializable

