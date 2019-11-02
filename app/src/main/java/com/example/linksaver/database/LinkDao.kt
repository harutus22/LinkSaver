package com.example.linksaver.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.linksaver.model.LinkModel

@Dao
interface LinkDao{
    @Query("SELECT * FROM LinkModel ORDER BY id")
    fun getAllLinks(): LiveData<List<LinkModel>>

    @Query("SELECT * FROM LinkModel ORDER BY priority DESC")
    fun getByPriority(): List<LinkModel>

    @Insert
    fun add(linkModel: LinkModel)

    @Update
    fun update(linkModel: LinkModel)

    @Delete
    fun delete(linkModel: LinkModel)
}