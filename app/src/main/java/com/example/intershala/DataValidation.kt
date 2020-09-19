package com.example.intershala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Script
import android.text.TextUtils
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intershala.Adapters.DataValidationAdapter
import com.example.intershala.Model.Userimfo
import com.google.firebase.database.*

class DataValidation : AppCompatActivity() {
lateinit var recyclerView: RecyclerView
     var list :ArrayList<Userimfo> =arrayListOf()
    lateinit var databaseReference:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_validation)
        recyclerView=findViewById(R.id.recyclerView)
        databaseReference=FirebaseDatabase.getInstance().getReference("UsersNumberTable")
    }

    override fun onStart() {
        super.onStart()
        databaseReference.addValueEventListener(
            object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                   //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    //To change body of created functions use File | Settings | File Templates.
                    list.clear()
                    var users =p0.children
                    for(i in users){
                        val owner=i.children
                        for(j in owner){
                            if(TextUtils.isDigitsOnly(j.key)){
                            val usersinfo=j.getValue(Userimfo::class.java)
                            list.add(usersinfo!!)
                           // System.out.println(j)
                                }
                        }
                    val adapter=DataValidationAdapter(this@DataValidation,list)
                        recyclerView.adapter=adapter
                        recyclerView.layoutManager= LinearLayoutManager(this@DataValidation)
                    }
                }

            }
        )
    }
}
