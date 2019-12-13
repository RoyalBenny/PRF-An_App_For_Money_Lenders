package com.prf.prf

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_renew.*

class RenewActivity : AppCompatActivity() {
var name:String?=null
    var number:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renew)

        var intent=intent
        name=   intent.getStringExtra("name")
        number=intent.getStringExtra("number").toInt()
        var db=DatabaseClass(this)
        var user=db.search(name!!)
        nameTextRenew.append(user.name)
        codeTextRenew.append(user.code)
        phoneTextRenew.append(user.phone)
        amountTextRenew.append(user.amount.toString())
        currentTextRenew.append(user.current.toString())
        expectedTextRenew.append(user.expected.toString())
        timeTextRenew.append(user.time.toString())

        saveButtonRenew.setOnClickListener{

            if(nameTextRenew.text.toString().length>0 &&
                    codeTextRenew.text.toString().length>0 &&
                    phoneTextRenew.text.toString().length>0 &&
                    amountTextRenew.text.toString().length>0 &&
                    currentTextRenew.text.toString().length>0 &&
                    expectedTextRenew.text.toString().length>0 &&
                    timeTextRenew.text.toString().length>0
            ){
                var nameOriginal=db.search(name!!)
                if(nameOriginal.name==nameTextRenew.text.toString()){
                    var user=Users()
                 user.name=   nameTextRenew.text.toString()
                 user.code=   codeTextRenew.text.toString()
                 user.phone=  phoneTextRenew.text.toString()
                 user.amount= amountTextRenew.text.toString().toInt()
                 user.current=currentTextRenew.text.toString().toInt()
                 user.expected= expectedTextRenew.text.toString().toInt()
                 user.time=     timeTextRenew.text.toString().toInt()

                    if(user.time<=100 && user.current <= user.time){
                        db.renew(user)
                        var db2=database(this)
                        if(user.current==user.time){
                            db2.renew(user.name,user.time,user.current-1)
                        }else{
                            db2.renew(user.name,user.time, user.current)
                        }

                        val chosen=Globals.Chosen

                        chosen.returnGlobals()!![userCurrentIx].code=user.code
                        chosen.returnGlobals()!![userCurrentIx].phone=user.phone
                        chosen.returnGlobals()!![userCurrentIx].amount=user.amount
                        chosen.returnGlobals()!![userCurrentIx].current =user.current
                        chosen.returnGlobals()!![userCurrentIx].expected=user.expected
                        chosen.returnGlobals()!![userCurrentIx].time=user.time
                        Snackbar.make(saveButtonRenew,"Renewed",Snackbar.LENGTH_SHORT).show()
                    }
                    else{
                        Snackbar.make(saveButtonRenew,"Please enter data correctly",Snackbar.LENGTH_SHORT).show()
                    }


                }
                else{
                    Snackbar.make(saveButtonRenew,"Sorry name must be same",Snackbar.LENGTH_SHORT).show()
                }

            }
            else{
                Snackbar.make(saveButtonRenew,"Please fill all data",Snackbar.LENGTH_SHORT).show()

            }




}



    }

    override fun onBackPressed() {

        if(number==1){
        var intent=Intent(applicationContext,Main2Activity::class.java)
        intent.putExtra("name",name)
        startActivity(intent)
        this.finish()
        }
        else if(number==2){
            var intent=Intent(applicationContext,Main3Activity::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
            this.finish()
        }
        super.onBackPressed()
    }
}
