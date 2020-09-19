package com.example.intershala

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.intershala.Model.USERFORM
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_data_entry.*

class DataEntry : AppCompatActivity() {
lateinit var btnEnterData:Button
    lateinit var txtApplicationSubmitted:TextView
    lateinit var txtPendendingApproval:TextView
    lateinit var txtApplicationApproved:TextView
    lateinit var txtLeadGenerated:TextView
    lateinit var txtApplicationRejected:TextView
    var number:String="0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)
        btnEnterData=findViewById(R.id.btnEnterData)
        txtApplicationApproved=findViewById(R.id.txtApplicationApproved)
        txtApplicationSubmitted=findViewById(R.id.txtApplicationSubmitted)
        txtApplicationRejected=findViewById(R.id.txtApplicationRejected)
        txtPendendingApproval=findViewById(R.id.txtPendendingApproval)
        txtLeadGenerated=findViewById(R.id.txtLeadGenerated)
         number=intent.getStringExtra("number")
//        txtLeadGenerated.text="0"
//        txtApplicationApproved.text="0"
//        txtApplicationRejected.text="0"

        initialize()
        btnEnterData.setOnClickListener {
            val intent = Intent(this@DataEntry,EnterUser::class.java)
            intent.putExtra("number",number)
            startActivity(intent)
           // finish()
        }


    }
   fun initialize(){
        val databaseReference= FirebaseDatabase.getInstance().getReference("UsersNumberTable")

        val href=databaseReference.child(number)
       databaseReference.child(number).addValueEventListener(
           object : ValueEventListener{
               override fun onCancelled(p0: DatabaseError) {
                    //To change body of created functions use File | Settings | File Templates.

               }

               override fun onDataChange(p0: DataSnapshot) {
                  //To change body of created functions use File | Settings | File Templates.
                   if(p0.exists()){
                       txtApplicationSubmitted.text= (p0.childrenCount - 5).toString()
                       txtPendendingApproval.text=p0.child("PendendingApproval").value.toString()
                       txtApplicationApproved.text=p0.child("ApplicationApproved").value.toString()
                       txtApplicationRejected.text=p0.child("ApplicationRejected").value.toString()
                       txtLeadGenerated.text=p0.child("LeadGenerated").value.toString()
                   }
                   else{
                       val userform=USERFORM(0,0,0,0,0)
                       databaseReference.child(number).setValue(userform)
                       val obj=databaseReference.child(number)
                       txtApplicationSubmitted.text= obj.child("ApplicationSubmitted").key
                       txtPendendingApproval.text= "0"
                    //   Toast.makeText(this@DataEntry,"${databaseReference.child(number)}",Toast.LENGTH_LONG).show()

                   }

               }

           }
       )
    }

    override fun onRestart() {
        super.onRestart()
        initialize()
    }
}
