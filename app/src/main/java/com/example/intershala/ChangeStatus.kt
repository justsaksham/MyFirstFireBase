package com.example.intershala

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.intershala.Model.DataCarrier
import com.example.intershala.Model.USERFORM
import com.example.intershala.Model.Userimfo
import com.google.firebase.database.*

class ChangeStatus : AppCompatActivity() {
    lateinit var txtPropertyName:TextView
    lateinit var txtPropertyLocation:TextView
    lateinit var txtLocality:TextView
    lateinit var txtOwnerNumber:TextView
    lateinit var txtOwnerName:TextView
    lateinit var txtLanguage:TextView
    lateinit var txtApplicationStatus:TextView
    lateinit var btnCallOwner:Button
    lateinit var spinner:Spinner
    lateinit var etCommentBox:EditText
    lateinit var btnSubmit:Button
    lateinit var USERFORm:USERFORM
    val REQUEST_CALL=1
    lateinit var userimfo:DataCarrier
    lateinit var userdata:Userimfo
    var Status:String=""
    lateinit var databaseReference:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_status)
        txtPropertyName=findViewById(R.id.txtPropertyName)
        txtPropertyLocation=findViewById(R.id.txtPropertyLocation)
        txtApplicationStatus=findViewById(R.id.txtApplicationStatus)
        txtLocality=findViewById(R.id.txtLocality)
        txtOwnerNumber=findViewById(R.id.txtOwnerNumber)
        txtOwnerName=findViewById(R.id.txtOwnerName)
        txtLanguage=findViewById(R.id.txtLanguage)
        btnCallOwner=findViewById(R.id.btnCallOwner)
        spinner = findViewById(R.id.spinner)
        etCommentBox = findViewById(R.id.etCommentBox)
        btnSubmit= findViewById(R.id.btnSubmit)


        databaseReference=FirebaseDatabase.getInstance().getReference("UsersNumberTable")

        userimfo = intent.getParcelableExtra<DataCarrier>("userimfo")






        txtLanguage.text="Preferred Language: ${userimfo.txtLanguage}"
        txtPropertyName.text="Property Name: ${userimfo.txtPropertyName}"
        txtPropertyLocation.text="Location: ${userimfo.txtPropertyLocation}"
        txtOwnerNumber.text="OWNER Number: ${userimfo.txtOwnerNumber}"
        txtOwnerName.text = "Owner Name :${userimfo.txtOwnerName}"
        txtLanguage.text="Prefered Language: ${userimfo.txtLanguage}"
        txtApplicationStatus.text="Application Status :${userimfo.txtApplicationStatus}"
        txtLocality.text="Locality: ${userimfo.txtLocality}"
            btnCallOwner.setOnClickListener {
                makecall()
            }
        btnSubmit.setOnClickListener {
            Status=""
            datasetChanged()
            Toast.makeText(this@ChangeStatus,"Status is saved Successfully ",Toast.LENGTH_LONG).show()
        }
    }
    fun  makecall(){
        var str=userimfo.txtOwnerNumber
        if(!TextUtils.isDigitsOnly(str)|| str.isEmpty()){
            Toast.makeText(this,"error in num", Toast.LENGTH_LONG).show()
        }
        else{
            str="tel:"+str
            //for getting permission during the run time
            if(ContextCompat.checkSelfPermission(this@ChangeStatus, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this@ChangeStatus, arrayOf(Manifest.permission.CALL_PHONE),REQUEST_CALL)
            }
            else {
                val intent= Intent(Intent.ACTION_CALL)
                intent.setData(Uri.parse(str))
                startActivity(intent)
            }

        }

    }
fun datasetChanged(){
  //  System.out.println(databaseReference.child(userimfo.txtUserNumber).child(userimfo.txtOwnerNumber))
    databaseReference.addListenerForSingleValueEvent(
        object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                //To change body of created functions use File | Settings | File Templates.
                System.out.println("saksham is great")
                USERFORm =p0.child(userimfo.txtUserNumber).getValue(USERFORM::class.java)!!

//                val a=(p0.child(userimfo.txtUserNumber).child("ApplicationApproved").value).toString().toInt()
//                val b=p0.child(userimfo.txtUserNumber).child("ApplicationRejected").value.toString().toInt()
//                val c=p0.child(userimfo.txtUserNumber).child("ApplicationSubmitted").value.toString().toInt()
//                val d=p0.child(userimfo.txtUserNumber).child("LeadGenerated").value.toString().toInt()
//                val e=p0.child(userimfo.txtUserNumber).child("PendendingApproval").value.toString().toInt()
//                USERFORm= USERFORM(c,a,e,b,d)
                 userdata= p0.child(userimfo.txtUserNumber).child(userimfo.txtOwnerNumber).getValue(Userimfo::class.java)!!
                System.out.println(userdata.status+" "+Status+" "+spinner.selectedItem)
               // Toast.makeText(this@ChangeStatus ,"${userdata!!.clientNumber} gfhfhgfhgfhgf",Toast.LENGTH_LONG).show()
                val value=spinner.selectedItem
                if(value.equals("N/A")|| userdata!!.status.equals(value) ){
                    Status=""
                    return
                }
                else{
                    displayChanges()
                    Status="changed"

                    return
                }
            }

        }
    )
}






    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==REQUEST_CALL){
            if(grantResults.size>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                makecall()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    fun displayChanges(){
            if(userdata.status.equals("Pending")&&spinner.selectedItem.equals("Approved")){
                USERFORm.PendendingApproval-=1
                USERFORm.ApplicationApproved+=1
                userdata.status="Approved"
            }
        else if(userdata.status.equals("Pending")&&spinner.selectedItem.equals("Rejected")){
                USERFORm.PendendingApproval-=1
                USERFORm.ApplicationRejected+=1
                userdata.status="Rejected"

            }
        else if(userdata.status.equals("Approved")&&spinner.selectedItem.equals("Rejected")){
                USERFORm.ApplicationApproved-=1
                USERFORm.ApplicationRejected+=1
                userdata.status="Rejected"
            }
        else if(userdata.status.equals("Approved")&&spinner.selectedItem.equals("Pending")){
                USERFORm.ApplicationApproved-=1
                USERFORm.PendendingApproval+=1
                userdata.status="Pending"

            }
        else if(userdata.status.equals("Rejected")&&spinner.selectedItem.equals("Pending")){
                USERFORm.ApplicationRejected-=1
                USERFORm.PendendingApproval+=1
                userdata.status="Pending"

        }
            else if(userdata.status.equals("Rejected")&&spinner.selectedItem.equals("Approved")){
                USERFORm.ApplicationRejected-=1
                USERFORm.ApplicationApproved+=1
                userdata.status="Approved"

            }
    databaseReference.child(userdata.userNumber).child(userdata.clientNumber).
    child("status").setValue(userdata.status)

        databaseReference.child(userdata.userNumber)
            .child("ApplicationRejected").setValue(USERFORm.ApplicationRejected)

        databaseReference.child(userdata.userNumber)
            .child("PendendingApproval").setValue(USERFORm.PendendingApproval)
        databaseReference.child(userdata.userNumber)
            .child("ApplicationApproved").setValue(USERFORm.ApplicationApproved)
       // databaseReference.child(userdata.userNumber).child(userdata.clientNumber)
       //     .child("ApplicationRejected").setValue(USERFORm.ApplicationRejected)
        txtApplicationStatus.text="Application Status :${userdata.status}"

        return
      }
}
