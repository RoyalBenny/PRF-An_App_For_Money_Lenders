package com.prf.prf

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*

var adapterdate:dateAdapter?=null
class Main2Activity : AppCompatActivity() {

    override  fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.detail_menu,menu)

        return super.onCreateOptionsMenu(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.detail_renew -> {


                    var intent=intent

                    var name=intent.getStringExtra("name")

                    var intent2=Intent(applicationContext,RenewActivity::class.java)
                    intent2.putExtra("name",name)
                    intent2.putExtra("number",1.toString())
                    startActivity(intent2)
                    this.finish()

            }
            R.id.detail_delete ->{
                val builder=AlertDialog.Builder(this)
                builder.setTitle("Delete")
                builder.setMessage("Do you want to delete?")

                builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int ->

                var intent=intent

                var name=intent.getStringExtra("name")
                var db=DatabaseClass(this)
                db.delete(name)

                var db2=database(this)
                db2.delete(name)


                var user=db.search(name)

                var db3=recentDatabase(this)
                db3.deleteUser(user.name)

                val chosen= Globals.Chosen
                chosen.returnGlobals()!!.removeAt(userCurrentIx)
                onBackPressed()


                this.finish()})

                builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
                builder.show()

            }


        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(findViewById(R.id.my_toolbar))


        var intent=intent

        var name=intent.getStringExtra("name")
//        var index=intent.getStringExtra("index").toString().toInt()
        completedText.visibility=View.INVISIBLE
        completeTick.visibility= View.INVISIBLE
        val db= DatabaseClass(this)
        var user=db.search(name)
       nameView.text=user.name
        codeView.text="{${user.code}}"
       phoneView.text=user.phone
        currentView.text="${user.current+1} - ${user.expected}"
        amountView.text=user.amount.toString()
        val db2=database(this)

        if(user.current==user.time){
            okButton.visibility= View.INVISIBLE
            amountText.visibility=View.INVISIBLE
            completeTick.visibility=View.VISIBLE
            completedText.visibility=View.VISIBLE
        }

        val ready_2= db2.read(user.name, user.time)
        val recyclerView=findViewById(R.id.dateRecycler) as RecyclerView
        recyclerView.layoutManager= GridLayoutManager(this,5)
        adapterdate= dateAdapter(ready_2)
        recyclerView.adapter=adapterdate
     //   DatemrkText.text=""

        amountText.hint = "${(user.amount) / (user.time)}"

        val chosen = Globals.Chosen
        var datas = chosen.returnGlobals()
        amountText.setOnKeyListener { v, keyCode, event ->

            if (keyCode== KeyEvent.KEYCODE_ENTER){
                okButton?.performClick()
            }

            return@setOnKeyListener false
        }
var click=1
        okButton.setOnClickListener {
            if(amountText.text.length!=0) {
                var amount = amountText.text.toString().toInt()
                if ((amount != 0) && (amount % (user.amount / user.time) == 0) && click==1) {
                    var startIx = user.current
                    var endIx = user.current + (amount / (user.amount / user.time))



                    db2.update(name, startIx, endIx, user.time)
                    val ready_2= db2.read(user.name, user.time)
                    val recyclerView=findViewById(R.id.dateRecycler) as RecyclerView
                    recyclerView.layoutManager= GridLayoutManager(this,5)

                    recyclerView.adapter=dateAdapter(ready_2)
                    completedText.visibility=View.INVISIBLE
                    completeTick.visibility= View.INVISIBLE

                    if ((startIx <= user.time) && (endIx <= user.time)) {
                        db.update(user.name, endIx)
                        click=2
                        Snackbar.make(okButton,"Changed to ${endIx+1}",Snackbar.LENGTH_LONG).show()
                    }
                    var userUpdate = db.search(name)
                    nameView.text=user.name
                    codeView.text="{${user.code}}"
                    phoneView.text=user.phone
                    currentView.text="${userUpdate.current+1} - ${userUpdate.expected}"
                    amountView.text=user.amount.toString()

                    chosen.returnGlobals()!![userCurrentIx].current = userUpdate.current
                    if (userUpdate.current == user.time) {
                        okButton.visibility = View.INVISIBLE
                        amountText.visibility = View.INVISIBLE
                        completedText.visibility=View.VISIBLE
                        completeTick.visibility= View.VISIBLE


                    }
                    amountText.text.clear()
                }else if(click==2){
                    amountText.text.clear()
                    Snackbar.make(okButton,"Amount already entered", Snackbar.LENGTH_SHORT).show()

                }
                else {
                    Snackbar.make(okButton,"Please Enter Correct Amount", Snackbar.LENGTH_SHORT).show()
                }


            }
        }


    }





    override fun onBackPressed() {

        listAdapter!!.notifyDataSetChanged()
        super.onBackPressed()

    }




}
