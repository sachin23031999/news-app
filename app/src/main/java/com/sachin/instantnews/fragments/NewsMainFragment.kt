package com.sachin.instantnews.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sachin.instantnews.R
import com.sachin.instantnews.adapters.ListAdapter
import com.sachin.instantnews.adapters.RecyclerViewAdapter
import com.sachin.instantnews.listeners.AdapterItemListener
import com.sachin.instantnews.models.News
import com.sachin.instantnews.models.SourceAdapter
import com.sachin.instantnews.networking.NetworkRequest
import com.sachin.instantnews.networking.VolleySingleton
import com.sachin.instantnews.parser.JsonParser
import com.sachin.instantnews.utility.Constants
import com.sachin.instantnews.utility.SharedPrefTask

class NewsMainFragment : Fragment(), AdapterItemListener {
    private val TAG = NewsMainFragment::class.java.toString()
    var mNewsList: ArrayList<News>? = null
    var recyclerView: RecyclerView? = null
    var mAdapter: RecyclerViewAdapter? = null
    var finalUrl: String? = null
    var sortSpinner: Spinner? = null
    var selectedLocation: String? = null
    var tvLocation: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "inside")
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        initToolbar()
        tvLocation = view.findViewById(R.id.tv_location)
        sortSpinner = view.findViewById<Spinner>(R.id.spinner_sort)
        val sortParams = arrayListOf("Popular", "Newest", "Oldest")
        if(sortSpinner != null) setSpinnerData(sortSpinner!!, sortParams)
        recyclerView = view.findViewById(R.id.news_recycler_view)
        mAdapter = RecyclerViewAdapter()
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = mAdapter
        mAdapter?.adapterItemListener = this
        return view
    }
    private fun spinnerListener(spinner: Spinner) {
        if (mAdapter != null && mNewsList != null) {
            when (spinner.selectedItem) {
                //Popular
                0 -> {
                    mAdapter!!.setData(mNewsList)
                }
                //Newest
                1 -> {
                    // Sort by Descending Time
                    //val sortedDescendingByDate = mNewsList..sort
                }
                //Oldest
                2 -> {
                    // Sort by Ascending Time
                }
            }
        }
    }
    private fun initToolbar() {
        val toolbarTitle = requireActivity().findViewById<TextView>(R.id.toolbar_title)
        val locationLayout = requireActivity().findViewById<LinearLayout>(R.id.location_layout)
        val backButton = requireActivity().findViewById<TextView>(R.id.back_button)
        backButton.visibility = View.GONE
        toolbarTitle.text = "MyNEWS"
        toolbarTitle.visibility = View.VISIBLE
        locationLayout.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val response = SharedPrefTask.getInstance(requireContext()).getData(Constants.SharedPrefKeys.TOP_HEADLINES)
        mNewsList = JsonParser(requireContext()).parseTopHeadlineResponse(response)
        if(mNewsList != null) mAdapter?.setData(mNewsList)
        if(sortSpinner != null) spinnerListener(sortSpinner!!)
       // populateLocation()
        val searchView = view.findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val results: ArrayList<News>? = if(query != null && !TextUtils.isEmpty(query))
                    mAdapter?.searchNews(query)
                else mNewsList

                mAdapter?.setData(results)
                return false
            }

        })
        fabAction(view)


    }
    private fun setSpinnerData(spinner: Spinner, data: ArrayList<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            data.distinct()
        )
        spinner.adapter = adapter
    }


    override fun onItemSelected(item: News?) {
        val directions = NewsMainFragmentDirections.actionNewsMainFragmentToNewsDetailFragment(item)
        findNavController().navigate(directions)
    }

    private fun populateLocation() {
        val tvLocation = requireActivity().findViewById<TextView>(R.id.tv_location)
        tvLocation.setOnClickListener {
            //val filterDialogLayout = layoutInflater.inflate(R.layout.filter_by_source, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(R.layout.dialog_location)
            val applyButton = dialog.findViewById<Button>(R.id.button_apply)
            val radioGroup = dialog.findViewById<RadioGroup>(R.id.radio_group)
            val sourceList = ArrayList<SourceAdapter>()
            val locationList = arrayListOf("Nepal", "USA", "India", "Sri Lanka", "England", "Sweden", "Pacific Islands")
            setDataToRadioGroup(radioGroup, locationList)
            dialog.show()
            applyButton?.setOnClickListener {
                val selectedRadio = dialog.findViewById<RadioButton>(radioGroup?.checkedRadioButtonId!!)
                when(selectedRadio?.text) {
                    locationList[0] -> {
                        selectedLocation = Constants.Country.NEPAL
                    }
                    locationList[1] -> {
                        selectedLocation = Constants.Country.USA
                    }
                    locationList[2] -> {
                        selectedLocation = Constants.Country.INDIA
                    }
                    locationList[3] -> {
                        selectedLocation = Constants.Country.SRI_LANKA
                    }
                    locationList[4] -> {
                        selectedLocation = Constants.Country.ENGLAND
                    }
                    locationList[5] -> {
                        selectedLocation = Constants.Country.SWEDEN
                    }
                    locationList[6] -> {
                        selectedLocation = Constants.Country.PACIFIC_ISLANDS
                    }
                }
                finalUrl = "${Constants.BASE_URL}?country=${selectedLocation}&apiKey=${Constants.API_KEY}"
                if(tvLocation != null) tvLocation.text = selectedLocation
                apiCall()
                mAdapter?.setData(mNewsList)
                dialog.dismiss()
            }
        }
    }
    private fun apiCall() {
      /*  if(finalUrl != null) {
            val request = NetworkRequest(
                Request.Method.GET, finalUrl!!, null, { response ->
                    Log.d(TAG, "response location wise :$response")
                    mNewsList = JsonParser(requireContext()).parseTopHeadlineResponse(response.toString())
                    //SharedPrefTask.getInstance(applicationContext).saveData(response.toString())

                },
                { error ->
                    Log.d(TAG, "error :${error.message}")
                }, Constants.AUTH_KEY
            )
            VolleySingleton.getInstance(requireContext()).addToRequestQueue(request)
        }*/
    }
     private fun setDataToRadioGroup(radioGroup: RadioGroup?, locationList: ArrayList<String>) {
        locationList.forEach {
            val radioButton = RadioButton(requireContext())
            radioButton.id = View.generateViewId()
            radioButton.text = it
            radioGroup?.addView(radioButton)
        }
    }

    private fun fabAction(view: View) {
        val filterDialogButton = view.findViewById<FloatingActionButton>(R.id.fab_filter)
        filterDialogButton.setOnClickListener {
            //val filterDialogLayout = layoutInflater.inflate(R.layout.filter_by_source, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(R.layout.dialog_filter)
            val applyFilterButton = dialog.findViewById<Button>(R.id.button_apply_filter)
            val listView = dialog.findViewById<ListView>(R.id.list_view)
            val sourceList = ArrayList<SourceAdapter>()
            mNewsList?.forEach {
                if(it.source != null) sourceList.add(SourceAdapter(it.source, false))
            }
            val adapter = ListAdapter(requireActivity(), sourceList)
            adapter.setData(sourceList)
            listView?.adapter = adapter
            dialog.show()
            applyFilterButton?.setOnClickListener {
                mAdapter?.applyFilter(adapter.getCheckedSource())
                dialog.dismiss()
            }

        }
    }
}