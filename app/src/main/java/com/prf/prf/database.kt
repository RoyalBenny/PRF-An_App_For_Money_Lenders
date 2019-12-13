package com.prf.prf

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.io.File


val Database_Name="Saved"
val Table_Name="Data_user"
val Username_Col="user"
val Code_Col="code"
val Phone_Col="phone"
val Amount_Col="amount"
val Current_Col="current"
val Expected_Col="expected"
val Time_Col="time"

class DatabaseClass(var context :Context):SQLiteOpenHelper(context, Database_Name,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable="CREATE TABLE IF NOT EXISTS ${Table_Name} (${Username_Col} VARCHAR , ${Code_Col} VARCHAR, ${Phone_Col} VARCHAR, ${Amount_Col} INTEGER," +
                " ${Current_Col} INTEGER ,${Expected_Col} INTEGER, ${Time_Col} INTEGER)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun newUser(users: Users):Long{
        val db=this.writableDatabase
        var cv=ContentValues()

        cv.put(Username_Col,users.name)
        cv.put(Code_Col,users.code)
        cv.put(Phone_Col,users.phone)
        cv.put(Amount_Col,users.amount)
        cv.put(Current_Col,users.current)
        cv.put(Expected_Col,users.expected)
        cv.put(Time_Col,users.time)

        var result=db.insert(Table_Name,null,cv)

        return result
    }


    fun viewUser(): ArrayList<Users> {

        var list = ArrayList<Users>()
        val db=this.readableDatabase
        val  query="Select * from "+Table_Name
        val cursor=db.rawQuery(query,null)

        if(cursor.moveToFirst()){
        do {


            var temp = Users()
            temp.name = cursor.getString(cursor.getColumnIndex(Username_Col))
            temp.code = cursor.getString(cursor.getColumnIndex(Code_Col))
            temp.phone = cursor.getString(cursor.getColumnIndex(Phone_Col))
            temp.amount = cursor.getString(cursor.getColumnIndex(Amount_Col)).toInt()
            temp.current = cursor.getString(cursor.getColumnIndex(Current_Col)).toInt()
            temp.expected = cursor.getString(cursor.getColumnIndex(Expected_Col)).toInt()
            temp.time = cursor.getString(cursor.getColumnIndex(Time_Col)).toInt()
            list.add(temp)

        }while (    cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return list
    }





    fun search(name:String): Users {

        var list = Users()
        val db=this.readableDatabase
        val  query="SELECT * FROM $Table_Name WHERE $Username_Col LIKE '$name'"
        val cursor=db.rawQuery(query,null)

        if(cursor.moveToFirst()){
            do {



                list.name = cursor.getString(cursor.getColumnIndex(Username_Col))
                list.code = cursor.getString(cursor.getColumnIndex(Code_Col))
                list.phone = cursor.getString(cursor.getColumnIndex(Phone_Col))
                list.amount = cursor.getString(cursor.getColumnIndex(Amount_Col)).toInt()
                list.current = cursor.getString(cursor.getColumnIndex(Current_Col)).toInt()
                list.expected = cursor.getString(cursor.getColumnIndex(Expected_Col)).toInt()
                list.time = cursor.getString(cursor.getColumnIndex(Time_Col)).toInt()


            }while (    cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return list
    }


    fun update(name:String,current:Int){
        val db=this.writableDatabase
        val cv= ContentValues()
        var query="SELECT * FROM $Table_Name WHERE $Username_Col LIKE '$name'"
        val cursor=db.rawQuery(query,null)
        if(cursor.moveToFirst()){
            do{
                if(name==cursor.getString(cursor.getColumnIndex(Username_Col))){
                    cv.put(Current_Col,current)
                    db.update(Table_Name,cv, Username_Col+" =?", arrayOf(cursor.getString(cursor.getColumnIndex(Username_Col))))
                }
            }while (cursor.moveToNext())
        }
        db.close()
        cursor.close()
    }

    fun updateExpection(){
        val db=this.writableDatabase
        val cv= ContentValues()
        var query="SELECT * FROM $Table_Name  "
        val cursor=db.rawQuery(query,null)

        var expectIncrease=0
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(cursor.getColumnIndex(Expected_Col)).toInt()!=cursor.getString(cursor.getColumnIndex(Time_Col)).toInt()){

                    expectIncrease=(cursor.getString(cursor.getColumnIndex(Expected_Col)).toInt())+1
                    cv.put(Expected_Col,expectIncrease)
                    db.update(Table_Name,cv, Username_Col+" =?", arrayOf(cursor.getString(cursor.getColumnIndex(Username_Col))))


                }
            }while (cursor.moveToNext())
        }
        db.close()
        cursor.close()
    }



    fun searchRecycle(name:String):ArrayList<Users>{
        var userArray:ArrayList<Users> = arrayListOf()
        val db=this.readableDatabase
        val  query="SELECT * FROM $Table_Name WHERE $Username_Col LIKE '$name%'"
        val cursor=db.rawQuery(query,null)
        if(cursor.moveToFirst()){
            do {

                val list = Users()

                list.name = cursor.getString(cursor.getColumnIndex(Username_Col))
                list.code = cursor.getString(cursor.getColumnIndex(Code_Col))
                list.phone = cursor.getString(cursor.getColumnIndex(Phone_Col))
                list.amount = cursor.getString(cursor.getColumnIndex(Amount_Col)).toInt()
                list.current = cursor.getString(cursor.getColumnIndex(Current_Col)).toInt()
                list.expected = cursor.getString(cursor.getColumnIndex(Expected_Col)).toInt()
                list.time = cursor.getString(cursor.getColumnIndex(Time_Col)).toInt()
                userArray.add(list)
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return userArray


    }



    fun delete(name:String){
        var db=this.writableDatabase
        db.delete(Table_Name, Username_Col+" =?", arrayOf(name))
    }

    fun renew(users: Users){
        var db=this.writableDatabase
        var cv=ContentValues()
        cv.put(Username_Col,users.name)
        cv.put(Code_Col, users.code)
        cv.put(Phone_Col,users.phone)
        cv.put(Amount_Col,users.amount)
        cv.put(Current_Col,users.current)
        cv.put(Expected_Col,users.expected)
        cv.put(Time_Col,users.time)
        db.update(Table_Name,cv, Username_Col+" =?" , arrayOf(users.name))
    }

    fun holiday(){
        val db=this.writableDatabase
        val cv= ContentValues()
        var query="SELECT * FROM $Table_Name  "
        val cursor=db.rawQuery(query,null)

        var expectDecre=0
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(cursor.getColumnIndex(Expected_Col)).toInt()!=cursor.getString(cursor.getColumnIndex(Time_Col)).toInt()  && cursor.getString(cursor.getColumnIndex(Expected_Col)).toInt()!=0){

                    expectDecre=(cursor.getString(cursor.getColumnIndex(Expected_Col)).toInt())-1
                    cv.put(Expected_Col,expectDecre)
                    db.update(Table_Name,cv, Username_Col+" =?", arrayOf(cursor.getString(cursor.getColumnIndex(Username_Col))))


                }
            }while (cursor.moveToNext())
        }
        db.close()
        cursor.close()
    }

    fun IncreaseExcept(){
        val db=this.writableDatabase
        val cv= ContentValues()
        var query="SELECT * FROM $Table_Name  "
        val cursor=db.rawQuery(query,null)

        var expectDecre=0
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(cursor.getColumnIndex(Expected_Col)).toInt()!=cursor.getString(cursor.getColumnIndex(Time_Col)).toInt()){

                    expectDecre=(cursor.getString(cursor.getColumnIndex(Expected_Col)).toInt())+1
                    cv.put(Expected_Col,expectDecre)
                    db.update(Table_Name,cv, Username_Col+" =?", arrayOf(cursor.getString(cursor.getColumnIndex(Username_Col))))


                }
            }while (cursor.moveToNext())
        }
        db.close()
        cursor.close()
    }



}
