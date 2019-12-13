package com.prf.prf


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.notecardview.*


class noteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val db= DataBase_Note(con!!)
        var list=db.readNotes()

        var adpaternote=noteClassAdapter(list)

        val rootView=inflater.inflate(R.layout.fragment_note, container, false)

        val recyclerViewnote=rootView.findViewById(R.id.noterecyclerView) as RecyclerView

        recyclerViewnote.layoutManager=LinearLayoutManager(activity)
        recyclerViewnote.adapter=adpaternote
        return rootView



    }




}
