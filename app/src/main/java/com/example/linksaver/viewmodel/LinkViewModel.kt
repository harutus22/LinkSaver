package com.example.linksaver.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.linksaver.model.LinkModel
import com.example.linksaver.repository.Repository

class LinkViewModel(app: Application): AndroidViewModel(app) {
    private val repo = Repository(app)

    fun getAllLinks(): LiveData<MutableList<LinkModel>>{
        return repo.getAllLinks()
    }

    fun addLink(linkModel: LinkModel){
        repo.saveLink(linkModel)
    }

    fun updateLink(linkModel: LinkModel){
        repo.updateLink(linkModel)
    }

    fun deleteLink(linkModel: LinkModel){
        repo.deleteLink(linkModel)
    }

    fun getAllByPriority(): MutableList<LinkModel>{
        return repo.getAllByPriority()
    }

    fun getAllByType(linkType: String): MutableList<LinkModel>{
        return repo.getCurrentType(linkType)
    }
}