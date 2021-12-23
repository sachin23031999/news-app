package com.sachin.instantnews.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sachin.instantnews.R
import com.sachin.instantnews.listeners.AdapterItemListener
import com.sachin.instantnews.models.News
import com.sachin.instantnews.models.SourceAdapter
import com.sachin.instantnews.utility.Utility

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var mData = mutableListOf<News>()
    var adapterItemListener: AdapterItemListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_news, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val tvSource = holder.itemView.findViewById<TextView>(R.id.tv_source)
        val tvDescription = holder.itemView.findViewById<TextView>(R.id.tv_detail)
        val tvTimeStamp = holder.itemView.findViewById<TextView>(R.id.tv_time_stamp)
        val ivNewsIconView = holder.itemView.findViewById<ImageView>(R.id.img_view_icon)

        val newsItem = mData[pos]
        if(tvSource != null && newsItem.source != null) tvSource.text = newsItem.source
        if(tvDescription != null && newsItem.description != null) tvDescription.text = newsItem.description
        if(tvTimeStamp != null && newsItem.timeStamp != null) tvTimeStamp.text = Utility().getTimeDifference(newsItem.timeStamp)
        if(ivNewsIconView != null && newsItem.imageUrl != null) {
            Utility().loadImage(ivNewsIconView, newsItem.imageUrl)
        }
        val cardView = holder.itemView.findViewById<CardView>(R.id.adapter_card_view)
        cardView.setOnClickListener {
            adapterItemListener?.onItemSelected(newsItem)
        }

    }


    override fun getItemCount(): Int {
       return mData.size
    }
    fun setData(data: ArrayList<News>?) {
        if(data != null) this.mData = data
        notifyDataSetChanged()
    }

    fun searchNews(query: String): ArrayList<News> {
        val results = ArrayList<News>()
        mData.forEach {
            if(it.description != null && it.description.contains(query)) {
                results.add(it)
            }
        }
        return results
    }
    fun applyFilter(sources: List<SourceAdapter>) {
        val filtered = ArrayList<News>()
        mData.forEach { news ->
            sources.forEach {
                if(it.source == news.source) filtered.add(news)
            }
        }
        this.mData = filtered
        notifyDataSetChanged()
    }
}