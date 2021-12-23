package com.sachin.instantnews.adapters

import android.content.Context
import android.widget.ArrayAdapter
import com.sachin.instantnews.models.SourceAdapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.sachin.instantnews.R

class ListAdapter(
    private val context: Activity,
    private var list: ArrayList<SourceAdapter>
) : ArrayAdapter<SourceAdapter>(context, R.layout.adapter_source, list) {

    private var filteredList = ArrayList<SourceAdapter>()
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.adapter_source, null, true)

        val tvSource = rowView.findViewById<TextView>(R.id.tv_source)
        val checkBox = rowView.findViewById<CheckBox>(R.id.source_checkbox)
        var item = list[position]
        tvSource.text = item.source
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                filteredList.add(item)
                item.isChecked = true
            }
            else filteredList = list
           // if(checkBox.isChecked) item.isChecked = true
           // notifyDataSetChanged()
        }

        return rowView
    }
    fun setData(sourceList: ArrayList<SourceAdapter>) {
        this.list = sourceList
        notifyDataSetChanged()
    }
    fun getCheckedSource(): ArrayList<SourceAdapter> {
        return filteredList

        /*list.forEach {
            if (it.isChecked) filteredList.add(it.source)
        }
        filteredList*/
    }
}