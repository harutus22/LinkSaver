package com.example.linksaver.viewmodel

import androidx.room.Database
import com.example.linksaver.model.LinkModel

@Database(entities = [LinkModel::class], version = 1)
class Database