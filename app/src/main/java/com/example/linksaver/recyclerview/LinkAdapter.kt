package com.example.linksaver.recyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.linksaver.R
import com.example.linksaver.model.LinkModel


class LinkAdapter(
    var linkModelList: MutableList<LinkModel>,
    private val onLinkItemClickListener: OnLinkItemClickListener
) :
    RecyclerView.Adapter<LinkAdapter.LinkViewHolder>() {
    private var itemsCopy: MutableList<LinkModel> = mutableListOf()

    init {
        itemsCopy.addAll(linkModelList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.link_item_view, parent, false)
        return LinkViewHolder(view, onLinkItemClickListener)
    }

    override fun getItemCount(): Int {
        return linkModelList.size
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        val linkItem = linkModelList[position]
        holder.linkUrl.text = linkItem.address
        holder.linkDescription.text = linkItem.description
        holder.linkPriority.setBackgroundColor(getColor(linkItem.priority))
        holder.linkImage.setImageResource(R.drawable.ic_launcher_foreground)
    }

    fun updateList(list: MutableList<LinkModel>) {
        linkModelList.clear()
        itemsCopy.clear()
        itemsCopy.addAll(list)
        linkModelList.addAll(list)
        notifyDataSetChanged()
    }

    fun linkAt(position: Int): LinkModel {
        return linkModelList[position]
    }


    inner class LinkViewHolder(itemView: View, onLinkItemClickListener: OnLinkItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val linkImage = itemView.findViewById<ImageView>(R.id.linkImage)
        val linkUrl = itemView.findViewById<TextView>(R.id.linkUrl)
        val linkDescription = itemView.findViewById<TextView>(R.id.linkDescription)
        val linkPriority = itemView.findViewById<ImageView>(R.id.linkPriority)

        init {
            itemView.setOnClickListener {
                onLinkItemClickListener.onItemClick(linkModelList[adapterPosition])
            }
        }
    }

    fun filter(text: String) {
        linkModelList.clear()
        if (text.isEmpty()) {
            linkModelList.addAll(itemsCopy)
        } else {
            val newText = text.toLowerCase()
            for (item in itemsCopy) {
                if (item.description!!.toLowerCase().contains(newText) || item.imageUri!!.toLowerCase().contains(
                        newText
                    )
                ) {
                    linkModelList.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

    private fun getColor(priority: Int): Int {
        return when (priority) {
            1 -> Color.GREEN
            2 -> Color.BLUE
            3 -> Color.YELLOW
            4 -> Color.CYAN
            else -> Color.RED
        }
    }
}

interface OnLinkItemClickListener {
    fun onItemClick(linkModel: LinkModel)
}