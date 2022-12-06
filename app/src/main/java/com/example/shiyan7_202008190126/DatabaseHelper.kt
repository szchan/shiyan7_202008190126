package com.example.shiyan7_202008190126

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context,name:String,version:Int): SQLiteOpenHelper(context,name,null,version){
    companion object{
        const val TABLENAME="relation"
    }
    private val createTable="create table $TABLENAME (_id integer primary key autoincrement,name text,tel text,groupName text)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if(oldVersion<newVersion){
            val sql="DROP TABLE IF EXISTS $TABLENAME"
            db.execSQL(sql)
            onCreate(db)
        }

    }

}