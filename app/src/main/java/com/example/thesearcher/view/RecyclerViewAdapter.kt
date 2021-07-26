package com.example.thesearcher.view

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thesearcher.R
import com.example.thesearcher.model.ImageInfo

class RecyclerViewAdapter(private var imagesList: MutableList<ImageInfo>)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.imageView.context).load(
            imagesList[position].thumbnailUrl
        ).into(holder.imageView)
    }


    override fun getItemCount(): Int {
        return imagesList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.itemImageView)

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ViewPagerActivity::class.java)
                intent.putExtra("ARG", this.layoutPosition) // TODO :: Refactor
                itemView.context.startActivity(intent)
            }
        }
    }


    fun update(data: MutableList<ImageInfo>){
        imagesList = data
        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
}