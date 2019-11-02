package com.example.linksaver.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.linksaver.R
import com.example.linksaver.model.LinkModel
import com.example.linksaver.recyclerview.OnLinkItemClickListener

class MainActivity : AppCompatActivity(), OnLinkItemClickListener {
    override fun onItemClick(linkModel: LinkModel) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
