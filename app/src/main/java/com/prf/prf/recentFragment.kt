package com.prf.prf


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList

var adapter2:classAdapter?=null


class recentFragment : Fragment() {
    var subscribe: Disposable?=null
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
        adapter=classAdapter( globalObject.globalList!!)



        val rootView=inflater.inflate(R.layout.fragment_recent, container, false)

        val recyclerView=rootView.findViewById(R.id.recyclerView_recent) as RecyclerView



        recyclerView.layoutManager= LinearLayoutManager(activity)
        recyclerView.adapter = adapter




        subscribe=adapter!!.clickEvent?.subscribe {

            var intent = Intent(con!!, Main2Activity::class.java)

            intent.putExtra("name", it.name)
            intent.putExtra("index",dataRecentUser.indexOf(it).toString())

            startActivity(intent)

        }










        return rootView


    }
    override fun onDestroy() {
        super.onDestroy()
        subscribe?.dispose()


    }


}
