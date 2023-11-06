package com.example.temax.classes

import org.json.JSONArray
import org.json.JSONObject
import java.sql.Date

class User(var UserID: Int, var Permission_level: Int, var Password: String, var Name: String, var Emai: String, var Date_birth: Date, var Contact: Int) {

    companion object {
        fun importFromJSONObject(obj: JSONObject): User {
            val dateOfBirthStr = obj.getString("Date_birth")
            val dateOfBirth = Date.valueOf(dateOfBirthStr) // Parse the date from the string


            return User(obj.getInt("UserID"),
                obj.getInt("Permission_level"),
                obj.getString("Password"),
                obj.getString("Name"),
                obj.getString("Email"),
                dateOfBirth,
                obj.getInt("Contact")
            )
        }

        fun importFromJSONArray(array: JSONArray): List<User>{
            var users = mutableListOf<User>()
            for(i in 0..array.length()-1){

                users.add(User.importFromJSONObject(array.getJSONObject(i)))
            }
            return users
        }
    }
}