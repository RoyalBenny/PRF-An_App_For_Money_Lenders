package com.prf.prf

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import kotlin.collections.ArrayList


val DATABASE_NAME="Mydata"
val TABLE_NAME="Mytable"
val USER_NAME="user"
var c= Calendar.getInstance()
var year=c.get(Calendar.YEAR)
val month=c.get(Calendar.MONTH)
var date=c.get(Calendar.DATE)
var day="$date/${month+1}/$year"

class database(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {

        var data="CREATE TABLE IF NOT EXISTS $TABLE_NAME ($USER_NAME VARCHAR, COL_1 VARCHAR, COL_2 VARCHAR, COL_3 VARCHAR, COL_4 VARCHAR, COL_5 VARCHAR, COL_6 VARCHAR, COL_7 VARCHAR, COL_8 VARCHAR, COL_9 VARCHAR, COL_10 VARCHAR , COL_11 VARCHAR, COL_12 VARCHAR, COL_13 VARCHAR, COL_14 VARCHAR, COL_15 VARCHAR, COL_16 VARCHAR, COL_17 VARCHAR, COL_18 VARCHAR, COL_19 VARCHAR, COL_20 VARCHAR ,COL_21 VARCHAR, COL_22 VARCHAR, COL_23 VARCHAR, COL_24 VARCHAR, COL_25 VARCHAR, COL_26 VARCHAR, COL_27 VARCHAR, COL_28 VARCHAR, COL_29 VARCHAR, COL_30 VARCHAR, COL_31 VARCHAR, COL_32 VARCHAR, COL_33 VARCHAR, COL_34 VARCHAR, COL_35 VARCHAR, COL_36 VARCHAR, COL_37 VARCHAR, COL_38 VARCHAR, COL_39 VARCHAR, COL_40 VARCHAR , COL_41 VARCHAR, COL_42 VARCHAR, COL_43 VARCHAR, COL_44 VARCHAR, COL_45 VARCHAR, COL_46 VARCHAR, COL_47 VARCHAR, COL_48 VARCHAR, COL_49 VARCHAR, COL_50 VARCHAR , COL_51 VARCHAR, COL_52 VARCHAR, COL_53 VARCHAR, COL_54 VARCHAR, COL_55 VARCHAR, COL_56 VARCHAR, COL_57 VARCHAR, COL_58 VARCHAR, COL_59 VARCHAR, COL_60 VARCHAR , COL_61 VARCHAR, COL_62 VARCHAR, COL_63 VARCHAR, COL_64 VARCHAR, COL_65 VARCHAR, COL_66 VARCHAR, COL_67 VARCHAR, COL_68 VARCHAR, COL_69 VARCHAR, COL_70 VARCHAR , COL_71 VARCHAR, COL_72 VARCHAR, COL_73 VARCHAR, COL_74 VARCHAR, COL_75 VARCHAR, COL_76 VARCHAR, COL_77 VARCHAR, COL_78 VARCHAR, COL_79 VARCHAR, COL_80 VARCHAR , COL_81 VARCHAR, COL_82 VARCHAR, COL_83 VARCHAR, COL_84 VARCHAR, COL_85 VARCHAR, COL_86 VARCHAR, COL_87 VARCHAR, COL_88 VARCHAR, COL_89 VARCHAR, COL_90 VARCHAR, COL_91 VARCHAR, COL_92 VARCHAR, COL_93 VARCHAR, COL_94 VARCHAR, COL_95 VARCHAR, COL_96 VARCHAR, COL_97 VARCHAR, COL_98 VARCHAR, COL_99 VARCHAR, COL_100 VARCHAR)"

        db?.execSQL(data)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    fun insert(name:String,time:Int):Long{

        val db=this.writableDatabase
        var cv= ContentValues()
        cv.put(USER_NAME,name)
        for (i in 1..time){
            cv.put("COL_$i","-")
        }
        val result=   db.insert(TABLE_NAME,null,cv)
        return result

    }


    fun update(name:String,startIx:Int,endIx:Int,time:Int){
        val db=this.writableDatabase
        val cv= ContentValues()
        val query="SELECT * FROM $TABLE_NAME WHERE $USER_NAME LIKE  '$name'"
        val cursor=db.rawQuery(query,null)
        if(cursor.moveToFirst()){
            do{
                if(name==cursor.getString(cursor.getColumnIndex(USER_NAME))){
                    if((startIx<=time)&&(endIx<=time)){
                    for (i in (startIx+1)..endIx){
                        cv.put("COL_$i", day)
                    }
                    db.update(TABLE_NAME,cv, USER_NAME+" =?", arrayOf(cursor.getString(cursor.getColumnIndex(USER_NAME))))
                }}
            }while (cursor.moveToNext())
        }
        db.close()
        cursor.close()
    }

    fun read(name: String,time:Int):ArrayList<String>{

        val map:ArrayList<String> = arrayListOf()
        val db=this.readableDatabase
        val query="SELECT * FROM $TABLE_NAME WHERE $USER_NAME LIKE '$name'"
        val cursor=db.rawQuery(query,null)

        if(cursor.moveToFirst()) {

            do {

                for (i in 1..time) {
                    map.add( cursor.getString(cursor.getColumnIndex("COL_$i")))

                }


            } while (cursor.moveToNext())
        }

        db.close()
        cursor.close()



        return map
    }
    fun delete(name:String){
        var db=this.writableDatabase
        db.delete(TABLE_NAME, USER_NAME+" =?", arrayOf(name))
    }

    fun renew(name: String,time: Int,current:Int){
        val db=this.writableDatabase
        var cv= ContentValues()

        if(current!=0){
        for (i in current+1..time){
            cv.put("COL_$i","-")
        }
        }else {
            for (i in 1..time) {
                cv.put("COL_$i", "-")
            }
        }
            db.update(TABLE_NAME,cv, USER_NAME+" =?", arrayOf(name))
    }
}