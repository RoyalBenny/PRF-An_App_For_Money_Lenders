package com.prf.prf

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

var DatabaseNote_Name="Note"
var Table_note_Name="Mynote"
var Title="title"
var Note_Col="note"

class DataBase_Note(var context: Context) : SQLiteOpenHelper(context, DatabaseNote_Name,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        var data="CREATE TABLE IF NOT EXISTS $Table_note_Name ($Title VARCHAR, $Note_Col VARCHAR)"
        db?.execSQL(data)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun inserNote(title:String,note:String) : Long {
        var db = this.writableDatabase
        var cv = ContentValues()
        cv.put(Title, title)
        cv.put(Note_Col, note)

        var result = db.insert(Table_note_Name, null, cv)
        return result
    }


    fun readNotes():ArrayList<noteClass>{

        var list:ArrayList<noteClass> = arrayListOf()
        var db=this.readableDatabase
        var query="SELECT * FROM $Table_note_Name"
        var cursor=db.rawQuery(query,null)
        if(cursor.moveToFirst()) {

            do {
                var key=cursor.getString(cursor.getColumnIndex(Title))
                var string=cursor.getString(cursor.getColumnIndex(Note_Col))
                var obj=noteClass(key,string)
                list.add(obj)
            } while (cursor.moveToNext())
        }
        db.close()
        cursor.close()
        return list

    }

    fun delete(title:String){
        var db=this.writableDatabase
        db.delete(Table_note_Name, Title+" =?", arrayOf(title))
    }

    fun search(title:String): String? {
        var db=this.readableDatabase
        var query="Select * from $Table_note_Name where $Title like '$title'"
        var cursor=db.rawQuery(query,null)
        var titleName:String?=null
        if(cursor.moveToFirst()){

            do{

                titleName=cursor.getString(cursor.getColumnIndex(Title))
            }while (cursor.moveToNext())
        }
        db.close()
        cursor.close()
        return titleName

    }


}