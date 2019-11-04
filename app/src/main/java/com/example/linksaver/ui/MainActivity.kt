package com.example.linksaver.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import java.io.FileNotFoundException

class MainActivity : AppCompatActivity(), OnLinkItemClickListener {
    private lateinit var linkViewModel: LinkViewModel
    private lateinit var linkAdapter: LinkAdapter

    override fun onItemClick(linkModel: LinkModel) {
        Toast.makeText(this, linkModel.priority.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linkViewModel = ViewModelProviders.of(this).get(LinkViewModel::class.java)
        linkViewModel.addLink(tryOuts())
        setUpRecyclerView()
        createItemTouchHelper()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        searchItem(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> return true

            R.id.sort -> return true

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun tryOuts(): LinkModel {
        return LinkModel(
            address = "http//someOfUsGoneToTheYel",
            description = "that is it",
            imageUri = "Query is a task",
            priority = 4,
            type = mutableListOf("supermasive, kronkTyoe")
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

    private fun searchItem(menu: Menu?) {
        val search = menu!!.findItem(R.id.menu_action_search)
        val mSearchView = search.actionView as androidx.appcompat.widget.SearchView
        mSearchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView(this),
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                linkAdapter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                linkAdapter.filter(newText)
                return true
            }

        })
    }

    private fun addImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        val mimeTypes = arrayListOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes)
        startActivityForResult(intent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    val imageUri = data?.data
                    val inputStream = contentResolver.openInputStream(imageUri!!)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
//                imageView.setImage
                } catch (e: FileNotFoundException){
                    e.printStackTrace()
                    Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "You have not picked up the image", Toast.LENGTH_LONG).show()
            }
        }
    }
}
