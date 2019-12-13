package com.prf.prf

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.util.Calendar
import android.os.Build
import android.support.annotation.RequiresApi
import java.util.*

val DATABASE_NAME_EXPECTION="Expection_database"
val TABLE_NAME_EXPECTION="Expection"
val DAY="Sunday"


class expection(var context: Context):SQLiteOpenHelper(context, DATABASE_NAME_EXPECTION,null,1){
    override fun onCreate(db: SQLiteDatabase?) {

        val expectionDatabase="CREATE TABLE IF NOT EXISTS $TABLE_NAME_EXPECTION ($DAY INTEGER)"

        db?.execSQL(expectionDatabase)

        val cv= ContentValues()
        cv.put(DAY,0)
        db?.insert(TABLE_NAME_EXPECTION,null,cv)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    fun changeExpection(){

        val calender=java.util.Calendar.getInstance()
        var day=calender.get(java.util.Calendar.DAY_OF_WEEK)
        var cv =ContentValues()
        var db= this.writableDatabase
        var query= "SELECT * FROM $TABLE_NAME_EXPECTION"
        var cursor= db.rawQuery(query,null)


        cursor.moveToFirst()

        if(((cursor.getString(cursor.getColumnIndex(DAY))).toInt()!=day) && (day!=1)){
            cv.put(DAY,day)
            db.update(TABLE_NAME_EXPECTION,cv, DAY+" =?", arrayOf(cursor.getString(cursor.getColumnIndex(DAY))))

            val data=DatabaseClass(context)

            data.updateExpection()

        }

        cursor.close()
    }




}