package com.sachin.instantnews.networking

import android.util.Base64
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class NetworkRequest(
    method: Int, url: String,
    jsonObject: JSONObject?,
    listener: Response.Listener<JSONObject>?,
    errorListener: Response.ErrorListener,
    credentials: String
) : JsonObjectRequest(method, url, jsonObject, listener, errorListener) {
    private var mCredentials: String = credentials

    override fun getHeaders(): MutableMap<String, String> {
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["User-Agent"] = "Mozilla/5.0"
        /*val auth = "Basic" + Base64.encodeToString(mCredentials.toByteArray(), Base64.NO_WRAP)
        headers["Authorization"] = auth*/
        return headers
    }
}