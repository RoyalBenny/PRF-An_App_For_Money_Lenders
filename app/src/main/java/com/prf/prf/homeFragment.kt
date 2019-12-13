package com.prf.prf


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ExpandableListView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.list_view.view.*
import java.util.*
import kotlin.collections.ArrayList

var listAdapter:ExpanadbleListAdapter? =null
class homeFragment : Fragment() {

    lateinit var searchView:SearchView
     var recyclerView:RecyclerView?=null
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var context=this
        var db=DatabaseClass(con!!)
        var recentDataStore=recentDatabase(con!!)

        var dateExpection= expection(con!!)

        dateExpection.changeExpection()

        var data=db.viewUser()

        var globalObject= Globals.Chosen
        globalObject.globalList=data
        Collections.sort(globalObject.globalList)

        var datas= globalObject.globalList




//        adapter=classAdapter( globalObject.globalList!!,2)
//
        val rootView=inflater.inflate(R.layout.fragment_home, container, false)
//
//
//        recyclerView=rootView.findViewById(R.id.recyclerView) as RecyclerView
//
//
//        recyclerView!!.layoutManager= LinearLayoutManager(activity)
//
//        recyclerView!!.adapter = adapter
        val expListView=rootView.findViewById<ExpandableListView>(R.id.expandableListView)

        listAdapter=ExpanadbleListAdapter(con!!,expListView,datas!!,2,datas)

        expListView.setAdapter(listAdapter)
        return rootView



    }
    


}

