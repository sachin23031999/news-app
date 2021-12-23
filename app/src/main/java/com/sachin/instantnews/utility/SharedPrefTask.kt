package com.sachin.instantnews.utility

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.sachin.instantnews.utility.Constants

class SharedPrefTask private constructor() {

    companion object {
        private var sharedPrefTask = SharedPrefTask()
        private lateinit var sharedPref: SharedPreferences

        fun getInstance(context: Context): SharedPrefTask {
            if (!::sharedPref.isInitialized) {
                synchronized(SharedPrefTask::class.java) {
                    if (!::sharedPref.isInitialized) {
                        sharedPref = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
                    }
                }
            }
            return sharedPrefTask
        }
    }

    private val TAG = "SharedPrefTask"

    fun getData(key: String?): String? {
        return if(sharedPref != null) {
            sharedPref!!.getString(key, "")
        }
        else null
    }
    fun saveData(data: String?) {
        Log.d(TAG, "saving data : $data")
        if(sharedPref != null) {
            val editor = sharedPref!!.edit()
            editor.putString(Constants.SharedPrefKeys.TOP_HEADLINES, data)
            editor.apply()
        }
    }
}