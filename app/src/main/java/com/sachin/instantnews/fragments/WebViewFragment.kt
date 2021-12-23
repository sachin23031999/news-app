package com.sachin.instantnews.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sachin.instantnews.R

class WebViewFragment : Fragment() {
    private val args: WebViewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsWebView = view.findViewById<WebView>(R.id.news_web_view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setWebView(newsWebView)
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setWebView(webViewContainer: WebView) {
        webViewContainer.webViewClient = WebViewClient()
        webViewContainer.apply {
            loadUrl(args.url)
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }
}