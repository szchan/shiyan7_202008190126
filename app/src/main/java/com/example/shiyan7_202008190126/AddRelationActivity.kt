package com.example.shiyan7_202008190126

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.additem.*

class AddRelationActivity : AppCompatActivity() {
    var dbHelper=DatabaseHelper(this, DATABASENAME,2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.additem)
        button1.setOnClickListener {
            val values=ContentValues().apply {
                put("name",addName.text.toString())
                put("tel",addTel.text.toString())
                put("groupName",spinner1.selectedItemId.toString())
            }
            AlertDialog.Builder(this).apply {
                setMessage(R.string.save_message)
                setPositiveButton(R.string.save){dialog, which ->
                    val db=dbHelper.writableDatabase
                    db.insert(DatabaseHelper.TABLENAME,null,values)
                    setResult(0x111,intent)
                    finish()
                }
                setNegativeButton(R.string.cancel){dialog, which -> }
                show()
            }
        }
    }
}
