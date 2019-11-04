package com.example.linksaver.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.linksaver.model.LinkModel
import com.example.linksaver.utils.Converters

@Database(entities = [LinkModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class LinkDatabase : RoomDatabase() {
    abstract fun linkDao(): LinkDao

    companion object {
        private var instance: LinkDatabase? = null

        fun getInstance(context: Context): LinkDatabase {
            if (instance == null){
                instance = buildDatabase(context)
            }
            return instance!!
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, LinkDatabase::class.java,
            "linkDatabase"
        ).build()
    }
}