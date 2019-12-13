package com.prf.prf

import android.app.Fragment
import android.app.FragmentTransaction
import android.app.PendingIntent.getActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast


import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class CreateActivity : AppCompatActivity() {
    var context = this
    var db = DatabaseClass(this)
    var db2 = database(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        test()


    }

    fun test() {
        saveButton.setOnClickListener {

            if (nameText.text.toString().length > 0
                    && phoneText.text.toString().length > 0
                    && codeText.text.toString().length > 0
                    && amountText.text.toString().length > 0
                    && currentText.text.toString().length > 0
                    && expectedText.text.toString().length > 0
                    && timeText.text.toString().length > 0
                    && amountText.text.toString().toInt()!=0
                    && timeText.text.toString().toInt()!=0
            ) {
                var entry = Users(nameText.text.toString(),
                        codeText.text.toString(),
                        phoneText.text.toString(),
                        amountText.text.toString().toInt(),
                        currentText.text.toString().toInt(),
                        expectedText.text.toString().toInt(),
                        timeText.text.toString().toInt())

                var nameCheck = nameText.text.toString()
                var check = db.search(nameCheck)

                if (check.name.length == 0 && timeText.text.toString().toInt()<=100) {
                    val result=db.newUser(entry)
                    val result2=db2.insert(nameText.text.toString(), timeText.text.toString().toInt())

                    if(result==-1.toLong()&& (result2==-1.toLong())){
                        Snackbar.make(findViewById(R.id.saveButton),"Failed",Snackbar.LENGTH_LONG).show()

                    }else{
                        Snackbar.make(findViewById(R.id.saveButton),"Saved",Snackbar.LENGTH_LONG).show()
                    }
                }else if(timeText.text.toString().toInt()>100){
                    Snackbar.make(findViewById(R.id.saveButton),"Time must be less than 100 ",Snackbar.LENGTH_LONG).show()

                }
                else {
                    Snackbar.make(findViewById(R.id.saveButton),"Name is already exist ",Snackbar.LENGTH_LONG).show()
                }

            } else {
                Snackbar.make(findViewById(R.id.saveButton),"Please fill data!",Snackbar.LENGTH_LONG).show()
            }
        }


    }



}