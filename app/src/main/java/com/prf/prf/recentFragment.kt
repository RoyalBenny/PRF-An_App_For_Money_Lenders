package com.prf.prf


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.Toast
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList


class recentFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var dbrecent=recentDatabase(con!!)
        var sortedRecent=ArrayList<String>()

        var dataRecent=   dbrecent.viewUser()
        for(i in dataRecent.lastIndex downTo 0){
            sortedRecent.add(dataRecent[i])
        }
        var globalObject= Globals.Chosen
        var dataRecentUser:ArrayList<Users> = ArrayList()
        var dataUser:Users
        sortedRecent.forEach{
            dataUser=dbrecent.recentCall(it)
            if(dataUser.amount!=0 && dataUser.time!=0){
            dataRecentUser.add(dataUser)
            }



        }
        globalObject.globalList=dataRecentUser
        val rootView=inflater.inflate(R.layout.fragment_recent, container, false)
//
//
//        recyclerView=rootView.findViewById(R.id.recyclerView) as RecyclerView
//
//
//        recyclerView!!.layoutManager= LinearLayoutManager(activity)
//
//        recyclerView!!.adapter = adapter
        val expListView=rootView.findViewById<ExpandableListView>(R.id.expandableRecentListView)

        listAdapter=ExpanadbleListAdapter(con!!,expListView,globalObject.returnGlobals(),1,globalObject.returnGlobals())

        expListView.setAdapter(listAdapter)







        return rootView


    }


}
