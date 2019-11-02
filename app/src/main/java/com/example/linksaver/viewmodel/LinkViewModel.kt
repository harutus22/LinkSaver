package com.example.linksaver.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.linksaver.model.LinkModel
import com.example.linksaver.repository.Repository

class LinkViewModel(app: Application): AndroidViewModel(app) {
    private val repo = Repository(app)

    fun getAllLinks(): LiveData<List<LinkModel>>{
        return repo.getAllLinks()
    }
}