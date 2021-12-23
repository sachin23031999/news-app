package com.sachin.instantnews.listeners

import com.sachin.instantnews.models.News

interface AdapterItemListener {
    fun onItemSelected(item: News?)
}