package com.codingbalance.currentnews

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class NewsListAdapter(private val listener: NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {
    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.itemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
        holder.titleTv.text = currentItem.title
        holder.authorTv.text = currentItem.author
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNews(updatedNews: ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTv: TextView = itemView.findViewById(R.id.titleTv)
    val image: ImageView = itemView.findViewById(R.id.newsImg)
    val authorTv: TextView = itemView.findViewById(R.id.authorTv)

}

// interface to handle clickListener of recylclerview item
interface NewsItemClicked{
    fun itemClicked(item: News)
}