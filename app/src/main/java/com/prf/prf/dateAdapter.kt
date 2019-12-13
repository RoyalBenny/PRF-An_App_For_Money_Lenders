package com.prf.prf

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlin.collections.ArrayList

class dateAdapter(val map:ArrayList<String>) : RecyclerView.Adapter<dateAdapter.ViewHolder>(){
   var i=1
    val context=this
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_card_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return map.size

    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.textViewColumn.text="${position.toString().toInt()+1}"

        holder.textViewDate.text=map[position].toString()
    }

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewColumn = itemView.findViewById(R.id.columnView) as TextView
        val textViewDate = itemView.findViewById(R.id.dateView) as TextView


    }

}