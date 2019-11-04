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


class LinkAdapter(var linkModelList: MutableList<LinkModel>,
                  private val onLinkItemClickListener: OnLinkItemClickListener):
    RecyclerView.Adapter<LinkAdapter.LinkViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.link_item_view, parent, false)
        return LinkViewHolder(view, onLinkItemClickListener)
    }

    override fun getItemCount(): Int {
        return linkModelList.size
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        val linkItem = linkModelList[position]
        holder.linkUrl.text = linkItem.address
        holder.linkDescription.text = linkItem.description
        holder.linkPriority.setBackgroundColor(Color.BLUE)
        holder.linkImage.setImageResource(R.drawable.ic_launcher_foreground)
    }

    fun updateList(list: MutableList<LinkModel>){
        linkModelList.clear()
        linkModelList.addAll(list)
        notifyDataSetChanged()
    }

    fun linkAt(position: Int): LinkModel{
        return linkModelList[position]
    }


    inner class LinkViewHolder(itemView: View, onLinkItemClickListener: OnLinkItemClickListener):
        RecyclerView.ViewHolder(itemView){
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
}

interface OnLinkItemClickListener{
    fun onItemClick(linkModel: LinkModel)
}