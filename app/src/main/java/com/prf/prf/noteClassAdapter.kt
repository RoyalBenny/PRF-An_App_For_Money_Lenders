package com.prf.prf

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.notecardview.view.*

class noteClassAdapter(val list:ArrayList<noteClass>) : RecyclerView.Adapter<noteClassAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): noteClassAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
        val view =v.inflate(R.layout.notecardview,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {

        return list.size

    }

    override fun onBindViewHolder(holder: noteClassAdapter.ViewHolder, position: Int) {
        val note:noteClass=noteClass()
        holder.titleView.text=list[position].title
        holder.noteView.text=list[position].note
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView = itemView.findViewById(R.id.titleView) as TextView
        val noteView = itemView.findViewById(R.id.noteView) as TextView

      init {
              itemView.noteDeleteButton.setOnClickListener {
                  val db =DataBase_Note(con!!)
                    db.delete(titleView.text.toString())
                    list.removeAt(layoutPosition)
                    notifyDataSetChanged()

              }

      }
    }
}