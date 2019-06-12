package com.prf.prf


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import io.reactivex.disposables.Disposable
import java.util.*

var adapter : classAdapter?= null
class homeFragment : Fragment() {
    var subscribe: Disposable?=null

    lateinit var searchView:SearchView

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




        adapter=classAdapter( globalObject.globalList!!)



        val rootView=inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView=rootView.findViewById(R.id.recyclerView) as RecyclerView



        recyclerView.layoutManager= LinearLayoutManager(activity)
        recyclerView.adapter = adapter




        subscribe=adapter!!.clickEvent?.subscribe {
           var intent = Intent(con!!, Main2Activity::class.java)
            intent.putExtra("index",data.indexOf(it).toString())
           intent.putExtra("name", it.name)

            recentDataStore.insertRecent(it.name)

           startActivity(intent)

        }






        return rootView




    }



    override fun onDestroy() {
        super.onDestroy()
        subscribe?.dispose()


    }

}

