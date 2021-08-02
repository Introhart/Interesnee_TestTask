package com.example.thesearcher.view

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thesearcher.R
import com.example.thesearcher.data.Network.Model.ImagesResult

class RecyclerViewAdapter(context: Context)
    : PagingDataAdapter<ImagesResult, ViewHolder>(ImageDiffCallback) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.recyclerview_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val imageView: ImageView = itemView.findViewById(R.id.itemImageView)

    init {
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, ViewPagerActivity::class.java)
            intent.putExtra("ARG", this.layoutPosition) // TODO :: Refactor
            itemView.context.startActivity(intent)
        }
    }

    fun bind(imageResult: ImagesResult?) {
        Glide.with(imageView.context).load(imageResult?.thumbnail).into(imageView)
    }
}

private object ImageDiffCallback : DiffUtil.ItemCallback<ImagesResult>() {

    override fun areItemsTheSame(oldItem: ImagesResult, newItem: ImagesResult): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImagesResult, newItem: ImagesResult): Boolean {
        return oldItem.title == newItem.title && oldItem.link == newItem.link
    }
}