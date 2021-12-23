package com.sachin.instantnews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sachin.instantnews.R
import com.sachin.instantnews.utility.Utility

class NewsDetailFragment : Fragment() {
    private val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_detail, container, false)
        initToolbar()
        return view
    }

    private fun initToolbar() {
        val toolbarTitle = requireActivity().findViewById<TextView>(R.id.toolbar_title)
        val locationLayout = requireActivity().findViewById<LinearLayout>(R.id.location_layout)
        val backButton = requireActivity().findViewById<TextView>(R.id.back_button)
        toolbarTitle.visibility = View.GONE
        locationLayout.visibility = View.GONE
        backButton.visibility = View.VISIBLE
        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        /*val direction = NewsDetailFragmentDirections.actionNewsDetailFragmentToNewsMainFragment()
            findNavController().navigate(direction)*/
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById<ImageView>(R.id.image_news)
        val tvSource = view.findViewById<TextView>(R.id.tv_source)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvLink = view.findViewById<TextView>(R.id.tv_link)
        val tvTimeStamp = view.findViewById<TextView>(R.id.tv_time_stamp)
        val tvContent = view.findViewById<TextView>(R.id.tv_content)

        val newsItem = args.news
        if(imageView != null && newsItem?.imageUrl != null) Utility().loadImage(imageView, newsItem.imageUrl)
        if(tvTitle != null && newsItem?.title != null) tvTitle.text = newsItem.title
        if(tvSource != null && newsItem?.source != null) tvSource.text = newsItem.source
        if(tvTimeStamp != null && newsItem?.timeStamp != null) tvTimeStamp.text = newsItem.timeStamp
        if(tvContent != null && newsItem?.content != null) tvContent.text = newsItem.content
        if(tvLink != null && newsItem?.newsUrl != null) {
            tvLink.setOnClickListener {
                //open chrome tab (web view) to view full news
                val directions = NewsDetailFragmentDirections.actionNewsDetailFragmentToWebViewFragment(newsItem.newsUrl)
                findNavController().navigate(directions)
            }
        }
    }


}