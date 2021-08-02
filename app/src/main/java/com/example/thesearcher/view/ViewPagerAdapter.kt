package com.example.thesearcher.view

import android.content.Context
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

//class ViewPagerAdapter(
//    var imagesList: MutableList<ImagesResult>
//) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {
//
//    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewPagerViewHolder {
//
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)
//        return ViewPagerViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewPagerAdapter.ViewPagerViewHolder, position: Int) {
//
//        val imageUrl = imagesList[position].original
//        val imageView = holder.itemView.findViewById<ImageView>(R.id.viewPagerImageView)
//        val button = holder.itemView.findViewById<Button>(R.id.button)
//
//        button.setOnClickListener {
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(imagesList[position].link))
//            holder.itemView.context.startActivity(browserIntent)
//        }
//
//        val res = Glide.with(imageView!!.context).load(imageUrl).into(imageView)
//        if(imageView.drawable == null){
//            Log.d("dbg", "Empty image")
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return imagesList.size
//    }
//}


class ViewPagerAdapter(context: Context)
    : PagingDataAdapter<ImagesResult, ViewPagerViewHolder>(ViewPagerImageDiffCallback) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(layoutInflater.inflate(R.layout.view_pager_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(getItem(position))
//        val imageUrl = imagesList[position].original
//        val imageView = holder.itemView.findViewById<ImageView>(R.id.viewPagerImageView)
//        val button = holder.itemView.findViewById<Button>(R.id.button)
//
//        button.setOnClickListener {
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(imagesList[position].link))
//            holder.itemView.context.startActivity(browserIntent)
//        }
//
//        val res = Glide.with(imageView!!.context).load(imageUrl).into(imageView)
//        if(imageView.drawable == null){
//            Log.d("dbg", "Empty image")
//        }
    }
}

class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val imageView = itemView.findViewById<ImageView>(R.id.viewPagerImageView)

    fun bind(imageResult: ImagesResult?) {
        Glide.with(imageView.context).load(imageResult?.original).into(imageView)
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