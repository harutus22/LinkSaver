package com.example.linksaver.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.linksaver.model.LinkModel

@Dao
interface LinkDao{
    @Query("SELECT * FROM link_table ORDER BY id")
    fun getAllLinks(): LiveData<MutableList<LinkModel>>

    @Query("SELECT * FROM link_table ORDER BY priority DESC")
    fun getByPriority(): MutableList<LinkModel>

    @Query("SELECT * FROM link_table WHERE type = :linkType")
    fun getThisType(linkType: String): MutableList<LinkModel>

    @Insert
    fun add(linkModel: LinkModel)

    @Update
    fun update(linkModel: LinkModel)

    @Delete
    fun delete(linkModel: LinkModel)
}