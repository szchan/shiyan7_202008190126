package com.example.shiyan7_202008190126

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

var DATABASENAME = "myrelation.db"

class MainActivity : AppCompatActivity() {
    private val relationList: MutableList<MyRelation> = ArrayList()
    var adapter: MyRelationAdapter? = null
    var dbHelper = DatabaseHelper(this, DATABASENAME, 2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val file = getDatabasePath(DATABASENAME)
        if (!file.exists()) {
            try {
                val input = resources.openRawResource(R.raw.myrelation)
                val fos = FileOutputStream(file)
                val buffer = ByteArray(input.available())
                var count = 0
                while (input.read(buffer).also { count = it } > 0) {
                    fos.write(buffer, 0, count)
                }
                fos.close()
                input.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val layoutManager=LinearLayoutManager(this)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        recyclerView1.layoutManager=layoutManager
        adapter= MyRelationAdapter(relationList)
        recyclerView1.adapter=adapter

        relationFromDB()
        imageView1.setOnClickListener {
            val intent= Intent(this,AddRelationActivity::class.java)
            startActivityForResult(intent,0x111)
        }
    }
    private fun relationFromDB(){
        val db=dbHelper.writableDatabase
        val cursor =
            db.query(DatabaseHelper.TABLENAME, null, null, null, null, null, null)
        relationList.clear()
        if (cursor.moveToFirst()) {
            for (i in 0 until cursor.count) {
                val relation = MyRelation()
                relation.id = cursor.getLong(0)
                relation.name = cursor.getString(1)
                relation.tel = cursor.getString(2)
                relation.gp = cursor.getString(3)
                relationList.add(relation)
                cursor.moveToNext()
            }
            adapter?.notifyDataSetChanged()
        } else
            Toast.makeText(this, R.string.find_fail, Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==0x111 && resultCode==0x111){
            relationFromDB()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
