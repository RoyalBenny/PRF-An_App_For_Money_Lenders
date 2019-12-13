package com.prf.prf

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
var click:Int?=null
var userCurrentIx=-1
class ExpanadbleListAdapter (var context: Context,var expandableListView: ExpandableListView,var parentArray:ArrayList<Users>,var number:Int,var childArray:ArrayList<Users>):BaseExpandableListAdapter(){
    override fun getGroup(groupPosition: Int): Users {
        return parentArray[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView=convertView
        if(convertView==null){
            val inflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView=inflater.inflate(R.layout.list_view,null)
        }
        val nameView=convertView?.findViewById(R.id.nameView) as TextView
        val amountView=convertView?.findViewById<TextView>(R.id.amountView)
        val currentView=convertView?.findViewById<TextView>(R.id.currentView)
        val arrowIcon= convertView?.findViewById<ImageView>(R.id.arrowIconList)
        val cardView=convertView?.findViewById<CardView>(R.id.cardViewList)
        val arrow=convertView?.findViewById<ImageView>(R.id.arrowIconList)
        val completeTick=convertView?.findViewById<ImageView>(R.id.completeImage)
        nameView?.text=getGroup(groupPosition).name
        amountView?.text=getGroup(groupPosition).amount.toString()
        if(parentArray[groupPosition].current!=parentArray[groupPosition].time){
        currentView.text="${getGroup(groupPosition).current+1} - ${getGroup(groupPosition).expected}"
            completeTick.visibility=View.GONE
            currentView.visibility=View.VISIBLE
        }
        else{
           currentView.visibility=View.GONE
            completeTick.visibility=View.VISIBLE
        }

        cardView?.setOnClickListener {

            if(expandableListView.isGroupExpanded(groupPosition))
            {

                expandableListView.collapseGroup(groupPosition)
            }
            else{
                if(click!=null){
                    expandableListView.collapseGroup(click!!)
                }
                expandableListView.expandGroup(groupPosition)

                if (number == 2) {
                    var db = recentDatabase(con!!)
                    db.insertRecent(parentArray[groupPosition].name)

                }
                click=groupPosition
            }

        }
        cardView?.setOnLongClickListener {
            arrow?.performClick()
            return@setOnLongClickListener  true
        }

        arrow?.setOnClickListener{
            val intent = Intent(con!!, Main2Activity::class.java)
            intent.putExtra("name", parentArray[groupPosition].name)
            userCurrentIx = groupPosition
            ContextCompat.startActivity(con!!, intent, null)
            if (number == 2) {
                var db = recentDatabase(con!!)
                db.insertRecent(parentArray[groupPosition].name)

            }
        }
        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Users {
        return childArray[groupPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        var convertView=convertView
        if(convertView==null){
            val inflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView=inflater.inflate(R.layout.expandable_list_view,null)
        }
        val amountText=convertView!!.findViewById(R.id.expandableAmountText) as EditText
        val okButton =convertView?.findViewById<Button>(R.id.expandableOkButton)
        val completedText=convertView?.findViewById<TextView>(R.id.expandableCompletedText)
        val completeTick=convertView?.findViewById<ImageView>(R.id.expandableCompleteTick)
        amountText!!.hint="${parentArray[groupPosition].amount/parentArray[groupPosition].time}"
        if (parentArray[groupPosition].current == parentArray[groupPosition].time) {
                   okButton?.visibility = View.GONE
                   amountText?.visibility = View.GONE
                   completeTick?.visibility=View.VISIBLE
                   completedText?.visibility= View.VISIBLE


               }
        else{
            completeTick?.visibility=View.GONE
            completedText?.visibility=View.GONE
            okButton?.visibility = View.VISIBLE
            amountText!!.visibility = View.VISIBLE
        }

        amountText!!.setOnKeyListener { v, keyCode, event ->

            if (keyCode==KeyEvent.KEYCODE_ENTER){
                okButton?.performClick()
            }

            return@setOnKeyListener false
        }

        okButton?.setOnClickListener{
            var db=DatabaseClass(con!!)
            var db2=database(con!!)
            if (amountText!!.text?.length != 0) {
                var user= parentArray[groupPosition]
                var amount = amountText!!.text.toString().toInt()
                if ((amount != 0) && (amount % (user.amount / user.time) == 0)) {
                    var startIx = user.current
                    var endIx = user.current + (amount / (user.amount / user.time))



                    db2.update(user.name, startIx, endIx, user.time)
                    val ready_2= db2.read(user.name, user.time)

                       completedText?.visibility=View.INVISIBLE
                       completeTick?.visibility= View.INVISIBLE

                    if ((startIx <= user.time) && (endIx <= user.time)) {
                        db.update(user.name, endIx)
                        Snackbar.make(okButton,"(${user.name}) Changed to ${endIx+1}", Snackbar.LENGTH_LONG).show()
                    }
                    var userUpdate = db.search(user.name)
                    var chosen=Globals.Chosen

                    chosen.returnGlobals()!![groupPosition].current = userUpdate.current
                    amountText?.text?.clear()
                    amountText?.hint=(user.amount/user.time).toString()
                  notifyDataSetChanged()
                    if (user.current == user.time) {
                        okButton.visibility = View.GONE
                       amountText?.visibility = View.GONE
                        completeTick?.visibility=View.VISIBLE
                        completedText?.visibility= View.VISIBLE


                    }
                } else {
                    Snackbar.make(okButton,"Please Enter Correct Amount", Snackbar.LENGTH_LONG).show()
                }

            }

        }
        return convertView

    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return  childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return parentArray.size
    }
}