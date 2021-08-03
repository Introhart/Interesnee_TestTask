package com.example.thesearcher.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thesearcher.R
import com.example.thesearcher.data.Network.Model.ImagesResult


class ViewPagerAdapter(context: Context)
    : PagingDataAdapter<ImagesResult, ViewPagerViewHolder>(ViewPagerImageDiffCallback) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(layoutInflater.inflate(R.layout.view_pager_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val imageView = itemView.findViewById<ImageView>(R.id.viewPagerImageView)
    private val btnOpenOriginalPage = itemView.findViewById<Button>(R.id.btnOpenOriginalPage)

    fun bind(imageResult: ImagesResult?) {
        Glide.with(imageView.context).load(imageResult?.original).into(imageView)

        btnOpenOriginalPage.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(imageResult?.link))
            itemView.context.startActivity(browserIntent)
        }
    }
}

// TODO :: Attention to ImgDiffCbk in RecyclerViewAdapter
private object ViewPagerImageDiffCallback : DiffUtil.ItemCallback<ImagesResult>() {

    override fun areItemsTheSame(oldItem: ImagesResult, newItem: ImagesResult): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImagesResult, newItem: ImagesResult): Boolean {
        return oldItem.title == newItem.title && oldItem.link == newItem.link
    }
}