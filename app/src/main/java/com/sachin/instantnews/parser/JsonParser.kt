package com.sachin.instantnews.parser

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.sachin.instantnews.utility.Constants
import com.sachin.instantnews.utility.SharedPrefTask
import com.sachin.instantnews.models.News
import org.json.JSONArray
import org.json.JSONObject

class JsonParser(private val context: Context) {

    fun parseTopHeadlineResponse(response: String?): ArrayList<News> {
        var data: JSONObject? = null
        if(response != null && !TextUtils.isEmpty(response)) {
                  data = JSONObject(response)
            Log.d("JsonParser", "response: $response")
        }
        val listNews = ArrayList<News>()
        if(data != null) {
            val articles =
                if (data.has(Constants.TopHeadKeys.ARTICLES)) data.optJSONArray(Constants.TopHeadKeys.ARTICLES)
                else null
            articles?.forEachObj {
                val author = if(it.has(Constants.TopHeadKeys.AUTHOR))
                                it.optString(Constants.TopHeadKeys.AUTHOR)
                            else null
                val title = if(it.has(Constants.TopHeadKeys.TITLE))
                                it.optString(Constants.TopHeadKeys.TITLE)
                            else null
                val description = if(it.has(Constants.TopHeadKeys.DESCRIPTION))
                                it.optString(Constants.TopHeadKeys.DESCRIPTION)
                            else null
                val url = if(it.has(Constants.TopHeadKeys.URL))
                    it.optString(Constants.TopHeadKeys.URL)
                else null
                val imageUrl = if(it.has(Constants.TopHeadKeys.IMAGE_URL))
                    it.optString(Constants.TopHeadKeys.IMAGE_URL)
                else null
                val timestamp = if(it.has(Constants.TopHeadKeys.TIME_STAMP))
                    it.optString(Constants.TopHeadKeys.TIME_STAMP)
                else null
                val source = if(it.has(Constants.TopHeadKeys.SOURCE)) {
                    val sourceJson = it.optJSONObject(Constants.TopHeadKeys.SOURCE)
                    if(sourceJson != null && sourceJson.has(Constants.TopHeadKeys.SOURCE_NAME))
                        sourceJson.optString(Constants.TopHeadKeys.SOURCE_NAME)
                    else null
                }
                else null
                val content = if(it.has(Constants.TopHeadKeys.CONTENT))
                    it.optString(Constants.TopHeadKeys.CONTENT)
                else null
                if((source != "null" && title != "null" && description != "null" && content != "null")){
                    val newsItem =
                        News(source, title, description, url, imageUrl, timestamp, content)
                    listNews.add(newsItem)
                }
            }
        }
        else {
            Log.d("JsonParser", "data is empty or null")
        }
        return listNews
    }

    private fun JSONArray.forEachObj(action: (JSONObject) -> Unit) {
        for(i in 0 until length()) {
            action(getJSONObject(i))
        }
    }

    private fun JSONArray.forEachString(action: (String) -> Unit) {
        for(i in 0 until length()) {
            action(getString(i))
        }
    }
}