package com.prf.prf

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASE_NAME_RECENT="Recent"
val TABLE_NAME_RECENT="RecentDatabsae"
val USER_NAME_RECENT="user"

class recentDatabase(var context: Context):SQLiteOpenHelper(context, DATABASE_NAME_RECENT,null,1){
    override fun onCreate(db: SQLiteDatabase?) {

        var data_recent="CREATE TABLE IF NOT EXISTS $TABLE_NAME_RECENT ($USER_NAME_RECENT VARCHAR)"

        db?.execSQL(data_recent)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertRecent(name:String){
        val db=this.writableDatabase
        var cv= ContentValues()
        cv.put(USER_NAME_RECENT,name)
        db.delete(TABLE_NAME_RECENT, USER_NAME_RECENT+" =?", arrayOf(name))
        db.insert(TABLE_NAME_RECENT,null,cv)
    }

    fun viewUser():ArrayList<String> {

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME_RECENT
        val cursor = db.rawQuery(query, null)

        var list = ArrayList<String>()

        if(cursor.moveToFirst()){
            do {
                var name = cursor.getString(cursor.getColumnIndex(Username_Col))
                list.add(name)

            }while (    cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return list



    }
    fun recentCall(name:String):Users{
        var database=DatabaseClass(con!!)
       var recent_data= database.search(name)
        return recent_data
    }

    fun deleteRecent(){
        var db=this.writableDatabase
        db.execSQL("delete from $TABLE_NAME_RECENT")

    }

    fun deleteUser(name: String){
        var db=this.writableDatabase
        db.execSQL("delete from $TABLE_NAME_RECENT where $USER_NAME_RECENT like '$name'")
    }



}


