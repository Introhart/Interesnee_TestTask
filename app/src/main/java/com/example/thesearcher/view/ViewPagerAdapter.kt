package com.example.thesearcher.view

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.thesearcher.R
import com.example.thesearcher.model.ImageInfo


//class ViewPagerAdapter(
//    fragment: FragmentActivity,
//    private var imagesList: MutableList<ImageInfo>,
//    ) : FragmentStateAdapter(fragment) {
//
//    override fun getItemCount(): Int = 30 // TODO : IMPLEMENT CORRECT REALISATION
//
//    override fun createFragment(position: Int): Fragment {
//        val fragment = PagerFragment()
//        fragment.arguments = Bundle().apply {
//            putString(ARG_OBJECT, imagesList[position].originalUrl) // TODO :: ???
//        }
//        return fragment
//    }
//
//    fun update(data: MutableList<ImageInfo>){
//        imagesList = data
//        notifyDataSetChanged()
//    }
//
//}

class ViewPagerAdapter(
    var imagesList: MutableList<ImageInfo>
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.ViewPagerViewHolder, position: Int) {
        val imageUrl = imagesList[position].originalUrl
        val imageView = holder.itemView.findViewById<ImageView>(R.id.viewPagerImageView)
        val button = holder.itemView.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(holder.itemView.context, WebViewActivity::class.java)
            intent.putExtra("ARG2", imagesList[position].linkPageUrl)
            holder.itemView.context.startActivity(intent)
        }
//        imageView.setImageResource(R.drawable.apple_original)
//        Glide.with(imageView!!.context).load(imageUrl).into(imageView)

        val res = Glide.with(imageView!!.context).load(imageUrl).into(imageView)
        if(imageView.drawable == null){
            Log.d("dbg", "Empty image")
        }

    }

    override fun getItemCount(): Int {
        return imagesList.size
    }


}