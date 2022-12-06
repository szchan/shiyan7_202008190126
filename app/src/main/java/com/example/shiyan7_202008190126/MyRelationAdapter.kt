package com.example.shiyan7_202008190126

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyRelationAdapter(private val myRelationList: MutableList<MyRelation>) :
    RecyclerView.Adapter<MyRelationAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var tel: TextView = view.findViewById(R.id.tel)
        var gp: TextView = view.findViewById(R.id.gp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.relationlist, parent, false)
        val holder = ViewHolder(view)
        val dbHelper = DatabaseHelper(parent.context, DATABASENAME, 2)
        holder.itemView.setOnClickListener {
            val relation = myRelationList[holder.adapterPosition]
            AlertDialog.Builder(parent.context).apply {
                setMessage(R.string.delete_message)
                setPositiveButton(R.string.confirm) { dialog, which ->
                    val db = dbHelper.readableDatabase
                    db.delete(DatabaseHelper.TABLENAME, "_id=?", arrayOf("" + relation.id))
                    val cursor =
                        db.query(DatabaseHelper.TABLENAME, null, null, null, null, null, null)
                    myRelationList.clear()
                    if (cursor.moveToFirst()) {
                        for (i in 0 until cursor.count) {
                            val relation = MyRelation()
                            relation.id = cursor.getLong(0)
                            relation.name = cursor.getString(1)
                            relation.tel = cursor.getString(2)
                            relation.gp = cursor.getString(3)
                            myRelationList.add(relation)
                            cursor.moveToNext()
                        }
                        this@MyRelationAdapter.notifyDataSetChanged()
                    } else
                        Toast.makeText(context, R.string.find_fail, Toast.LENGTH_LONG).show()
                }
                setNegativeButton(R.string.cancel) { dialog, which -> }
                show()
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val relation = myRelationList[position]
        holder.name.text = relation.name
        holder.tel.text = relation.tel
        holder.gp.text = relation.gp
    }

    override fun getItemCount() = myRelationList.size


}