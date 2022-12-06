package com.example.shiyan7_202008190126

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val pref=getSharedPreferences("data", Context.MODE_PRIVATE)
        val isRemember=pref.getBoolean("remember_password",false)
        if(isRemember){
            val acc=pref.getString("account","")
            val pass=pref.getString("password","")
            accountEdit.setText(acc)
            passwordEdit.setText(pass)
            remember_pass.isChecked=true
        }
        login.setOnClickListener {
            val account=accountEdit.text.toString()
            val password=passwordEdit.text.toString()

            if(account=="admin" && password=="123456"){
                val editor=pref.edit()
                if (remember_pass.isChecked){
                    editor.putBoolean("remember_password",true)
                    editor.putString("account",account)
                    editor.putString("password",password)
                }else{ editor.clear() }
                editor.apply()
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,R.string.login_fail, Toast.LENGTH_SHORT).show()
            }
        }
    }
}