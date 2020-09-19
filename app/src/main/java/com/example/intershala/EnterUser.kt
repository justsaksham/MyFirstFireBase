package com.example.intershala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.example.intershala.Model.USERFORM
import com.example.intershala.Model.Userimfo
import com.google.firebase.database.*

class EnterUser : AppCompatActivity() {
    lateinit var etclientNumber: EditText
    lateinit var btnValidateNumber: Button
    lateinit var ll: LinearLayout
    lateinit var Property: EditText
    lateinit var etCity: EditText
    lateinit var etArea: EditText
    lateinit var Owner: EditText
    lateinit var language: EditText
    lateinit var btnSubmit: Button
    lateinit var databaseReference: DatabaseReference
    var Number: String = ""
    var head=1
    var low=1
    var Userdata:USERFORM= USERFORM(0,0,0,0,0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_user)
        etclientNumber = findViewById(R.id.etclientNumber)
        btnValidateNumber = findViewById(R.id.btnValidateNumber)
        ll = findViewById(R.id.ll)
        Property = findViewById(R.id.Property)
        etCity = findViewById(R.id.etCity)
        etArea = findViewById(R.id.etArea)
        Owner = findViewById(R.id.Owner)
        language = findViewById(R.id.language)
        btnSubmit = findViewById(R.id.btnSubmit)
        databaseReference = FirebaseDatabase.getInstance().getReference("UsersNumberTable")
       // Toast.makeText(this@EnterUser, "$databaseReference", Toast.LENGTH_LONG).show()
        var number = intent.getStringExtra("number")
        Number = number
        val number2 = etclientNumber.text.toString()
        var h = 0
        btnValidateNumber.setOnClickListener {
            head=1
            low=1
           val jj= etclientNumber.text.toString()
            if(jj.length!=10){
                Toast.makeText(this@EnterUser,"$jj must have 10 digit",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(!TextUtils.isDigitsOnly(jj)){
                Toast.makeText(this@EnterUser,"$jj only digits are allowed",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            h = checkNumber(number, etclientNumber.text.toString())
           // head=h;

            initializeEdit()

        }
        btnSubmit.setOnClickListener {
            if(etArea.text.isEmpty()  || etArea==null){
                Toast.makeText(this,"Area is required field",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(Property.text.isEmpty()|| Property==null){
                Toast.makeText(this,"Property name is required field",Toast.LENGTH_LONG).show()
             return@setOnClickListener
            }
            if(etCity.text.isEmpty()|| etCity==null){
                Toast.makeText(this,"Property name is required field",Toast.LENGTH_LONG).show()
            return@setOnClickListener
            }
            if(Owner.text.isEmpty()){
            Owner.setText("N/A")
            }
            if(language.text.isEmpty()){
            language.setText("N/A")
            }

            if (head != 0) {
                low=0
                val info = Userimfo(
                    etclientNumber.text.toString(),
                    Property.text.toString(),
                    etCity.text.toString(),
                    Owner.text.toString(),
                    language.text.toString(),
                    Number,
                    "Pending"
                ,etArea.text.toString()
                )

                val applicationSubmitted = Userdata!!.ApplicationSubmitted+1
                val pendendingApproval =Userdata!!.PendendingApproval+1
                databaseReference.child(Number).child("PendendingApproval").setValue(pendendingApproval)
                databaseReference.child(Number).child("ApplicationSubmitted").setValue(applicationSubmitted)

                val ref = databaseReference.child(Number).child(etclientNumber.text.toString()).setValue(info)
                System.out.println(databaseReference.child(Number).child(etclientNumber.text.toString()).key)

                Toast.makeText(this@EnterUser,"SuccessFully Submitted",Toast.LENGTH_LONG).show()
                low=0
//                val intent = Intent(this@EnterUser,DataEntry::class.java)
//                startActivity(intent)
//             //   finishAffinity()
//                finish()
                ll.visibility = View.INVISIBLE

            }
        }
    }

    private fun initializeEdit() {
        var a:String=" "
        Property.setText("")
        etArea.setText("")
        etCity.setText("")
        Owner.setText("")
        language.setText("")

    }


    fun checkNumber(number:String,number2:String):Int{
       var h=0
       val ref=databaseReference.child(number)
       databaseReference.addListenerForSingleValueEvent(
           object :ValueEventListener{
               override fun onCancelled(p0: DatabaseError) {

               }

               override fun onDataChange(p0: DataSnapshot) {
                 //To change body of created functions use File | Settings | File Templates.
                   System.out.println("called")
                   val obj=p0.children
                   for(i in obj){
                      var j= i.children
                       for( l in j) {
                           if (l.key.toString().equals(etclientNumber.text.toString())) {
                               System.out.println(l.key.toString())
                               this@EnterUser.head = 0

                              break

                           }
                       }
                       if(head==0){
                           break;
                       }
//                       if(head==1){
//                           head=0
//                       }

//                       System.out.println(i.key.toString()+etclientNumber.text.toString())
//                       System.out.println(i.key.toString().equals(etclientNumber.text.toString()))

                   }
                   if (head != 0) {
                       ll.visibility = View.VISIBLE
                       Userdata = p0.child(Number).getValue(USERFORM::class.java)!!

                   } else {
                       ll.visibility = View.GONE
                       if(low==1) {
                           Toast.makeText(
                               this@EnterUser,
                               "You have already applied to this job",
                               Toast.LENGTH_LONG
                           ).show()

                       }

                   }
               }

           }
       )





        return h;
    }

//
//fun validateUser(ref:DatabaseReference):Int{
//    var h=0
//    ref.child(etclientNumber.text.toString()).addValueEventListener(
//        object :ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                //To change body of created functions use File | Settings | File Templates.
//              //  Toast.makeText(this@EnterUser,"$p0",Toast.LENGTH_LONG).show()
//                if(p0.key.equals(etclientNumber.text.toString())){
//                    head=1
//                  //  Toast.makeText(this@EnterUser,"$p0",Toast.LENGTH_LONG).show()
//                }
//                else{
//                    head=0;
//                }
//            }
//
//        }
//    )
//    Toast.makeText(this@EnterUser,"$h",Toast.LENGTH_LONG).show()
//    return h
//}
//
}