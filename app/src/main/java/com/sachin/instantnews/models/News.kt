package com.sachin.instantnews.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val source: String?,
    val title: String?,
    val description: String?,
    val newsUrl: String?,
    val imageUrl: String?,
    val timeStamp: String?,
    val content: String?
) : Parcelable