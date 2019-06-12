package com.prf.prf

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.prf.prf.R
import io.reactivex.subjects.PublishSubject
import java.util.*
import kotlin.collections.ArrayList
var userCurrentIx:Int=0
var userExpectInx=0
class classAdapter (val userList:ArrayList<Users>) :RecyclerView.Adapter<classAdapter.ViewHolder>() {


    val context = this

    val clickSubject = PublishSubject.create<Users>()
    val clickEvent: PublishSubject<Users>? = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
        val view = v.inflate(R.layout.list_view, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return userList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: Users = userList[position]
        holder.textViewName.text = user.name
        holder.textViewAmount.text = user.amount.toString()
        holder.textViewCurrent.text = user.current.toString() + " - " + user.expected.toString()


    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById(R.id.nameView) as TextView
        val textViewAmount = itemView.findViewById(R.id.amountView) as TextView
        val textViewCurrent = itemView.findViewById(R.id.currentView) as TextView

        init {
            itemView.setOnClickListener {
                clickSubject.onNext(userList[layoutPosition])

                userCurrentIx = layoutPosition
                userExpectInx=layoutPosition

            }


        }


    }


}




