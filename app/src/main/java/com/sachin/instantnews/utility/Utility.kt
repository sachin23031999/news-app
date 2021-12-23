package com.sachin.instantnews.utility

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.minutes

class Utility {
    fun getTimeDifference(timeStamp: String) : String? {
       /* val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ")
        val fetchedTime = formatter.parse(timeStamp)
        val currentTime = Calendar.getInstance().time
        val diff = currentTime.time - fetchedTime!!.time
     //   val formattedTime = formatter.format(time)
     //   Log.d("utility", "time: $formattedTime")
      //  return formattedTime
        return diff.minutes.toString()*/
    return timeStamp
    }

    @SuppressLint("CheckResult")
    fun loadImage(imageView: ImageView?, url: String?) {
        if(imageView != null && url != null) {
            Glide.with(imageView.context).load(url).listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.setImageDrawable(resource)
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).into(imageView)
        }
    }
}