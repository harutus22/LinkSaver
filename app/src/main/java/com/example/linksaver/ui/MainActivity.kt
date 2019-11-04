package com.example.linksaver.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.example.linksaver.R
import com.example.linksaver.model.LinkModel
import com.example.linksaver.recyclerview.LinkAdapter
import com.example.linksaver.recyclerview.OnLinkItemClickListener
import com.example.linksaver.viewmodel.LinkViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnLinkItemClickListener {
    private lateinit var linkViewModel: LinkViewModel
    private lateinit var linkAdapter: LinkAdapter

    override fun onItemClick(linkModel: LinkModel) {
        Toast.makeText(this, "whats up doc", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linkViewModel = ViewModelProviders.of(this).get(LinkViewModel::class.java)

        setUpRecyclerView()
        createItemTouchHelper()
    }

    private fun tryOuts(): LinkModel {
        return LinkModel(
            address = "http//aksjdfiadj/sadkjda",
            description = "this is a megalink",
            imageUri = "asdjoaidadaiduah",
            priority = 5,
            type = mutableListOf("wedding")
        )
    }

    private fun setUpRecyclerView() = with(recyclerView) {
        layoutManager = LinearLayoutManager(this@MainActivity)
        linkAdapter = LinkAdapter(mutableListOf(), this@MainActivity)
        adapter = linkAdapter
        val linkLiveData = linkViewModel.getAllLinks()
        linkLiveData.observe(this@MainActivity, Observer { t ->
            t.let {
                (adapter as LinkAdapter).updateList(t)
            }
        })
        itemAnimator = DefaultItemAnimator()
        addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun createItemTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(1, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val linkModel = linkAdapter.linkAt(viewHolder.adapterPosition)
                        val snackBar =
                            Snackbar.make(recyclerView, "Item deleted", Snackbar.LENGTH_LONG)
                        snackBar.setAction("Undo") {
                            linkViewModel.addLink(linkModel)
                            Snackbar.make(recyclerView, "Link restored", Snackbar.LENGTH_LONG)
                                .show()
                        }
                        linkViewModel.deleteLink(linkModel)
                        snackBar.show()
                    }
                }
            }
        }).attachToRecyclerView(recyclerView)
    }
}
