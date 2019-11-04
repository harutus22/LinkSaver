package com.example.linksaver.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class Converters: Serializable {
    @TypeConverter
    fun fromTypeList(type: List<String>?): String?{
        if(type == null) return null
        val gson = Gson()
        val typeObject = object : TypeToken<List<String>>() {}.type
        return gson.toJson(type, typeObject)
    }

    @TypeConverter
    fun toTypeList(value: String?): List<String>?{
        if(value == null) return null
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}