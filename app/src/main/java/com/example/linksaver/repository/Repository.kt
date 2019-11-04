package com.example.linksaver.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.linksaver.database.LinkDatabase
import com.example.linksaver.model.LinkModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Repository(context: Context) {
    private val linkDao by lazy { LinkDatabase.getInstance(context).linkDao() }

    fun getAllLinks(): LiveData<MutableList<LinkModel>>{
        return linkDao.getAllLinks()
    }

    fun saveLink(linkModel: LinkModel){
        GlobalScope.launch {
            linkDao.add(linkModel)
        }
    }

    fun deleteLink(linkModel: LinkModel){
        GlobalScope.launch {
            linkDao.delete(linkModel)
        }
    }

    fun updateLink(linkModel: LinkModel){
        GlobalScope.launch {
            linkDao.update(linkModel)
        }
    }

    fun getAllByPriority(): MutableList<LinkModel>{
        return linkDao.getByPriority()
    }

    fun getCurrentType(type: String): MutableList<LinkModel>{
        return linkDao.getThisType(type)
    }


}