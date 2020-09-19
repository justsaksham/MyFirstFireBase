package com.example.intershala

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class HomePage : AppCompatActivity() {
lateinit var btnDataEntry:Button
    lateinit var btnDataValidation:Button
    lateinit var btnLogout:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        btnDataEntry=findViewById(R.id.btnDataEntry)
        btnDataValidation=findViewById(R.id.btnDataValidation)
        btnLogout=findViewById(R.id.btnLogout)
        var number=intent.getStringExtra("number")
        btnDataEntry.setOnClickListener {
            val intent= Intent(this@HomePage,DataEntry::class.java)
            intent.putExtra("number",number)
            startActivity(intent)
        }
        btnLogout.setOnClickListener {
            val sharedPreferences=getSharedPreferences("Intern", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
            val intent=Intent(this@HomePage,MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        btnDataValidation.setOnClickListener {
            val intent= Intent(this@HomePage,DataValidation::class.java)
            intent.putExtra("number",number)
            startActivity(intent)
        }
    }
}
