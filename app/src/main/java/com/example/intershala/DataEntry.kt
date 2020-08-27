package com.example.intershala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
        txtLeadGenerated.text="0"
        txtApplicationApproved.text="0"
        txtApplicationRejected.text="0"

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
       databaseReference.child(number).addValueEventListener(
           object : ValueEventListener{
               override fun onCancelled(p0: DatabaseError) {
                    //To change body of created functions use File | Settings | File Templates.

               }

               override fun onDataChange(p0: DataSnapshot) {
                  //To change body of created functions use File | Settings | File Templates.
                   if(p0!=null){
                       txtApplicationSubmitted.text= p0.childrenCount.toString()
                       txtPendendingApproval.text=p0.childrenCount.toString()
                   }
                   else{
                       txtApplicationSubmitted.text= "0"
                       txtPendendingApproval.text= "0"

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
