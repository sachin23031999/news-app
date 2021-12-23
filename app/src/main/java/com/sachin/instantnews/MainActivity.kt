package com.sachin.instantnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.volley.Request
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sachin.instantnews.adapters.ListAdapter
import com.sachin.instantnews.models.SourceAdapter
import com.sachin.instantnews.networking.NetworkRequest
import com.sachin.instantnews.networking.VolleySingleton
import com.sachin.instantnews.utility.Constants
import com.sachin.instantnews.utility.SharedPrefTask
import com.sachin.instantnews.utility.Utility

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "inside main activity")
        //https://newsapi.org/v2/top-headlines?country=us&apiKey=$API_KEY
        val defaultUrl = "${Constants.BASE_URL}?country=${Constants.Country.INDIA}&apiKey=${Constants.API_KEY}"
        val request = NetworkRequest(
            Request.Method.GET, defaultUrl, null, { response ->
                Log.d(TAG, "response :$response")
                SharedPrefTask.getInstance(applicationContext).saveData(response.toString())

            },
            { error ->
                Log.d(TAG, "error :${error.message}")
            }, Constants.AUTH_KEY
        )
        VolleySingleton.getInstance(this).addToRequestQueue(request)

        val timeDiff = Utility().getTimeDifference("2021-12-22T03:30:06Z")

    }


}