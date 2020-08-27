package com.example.intershala

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.hbb20.CountryCodePicker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var etUserNumber:EditText
    lateinit var btnGetOtp:Button
    lateinit var ccp:CountryCodePicker
    lateinit var etOtp:EditText
    lateinit var btnSubmitOtp:Button
    var VarificationId:String?=null
    lateinit var sharedPreferences:SharedPreferences
    var Number:String="1234567890"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         sharedPreferences=getSharedPreferences("Intern", Context.MODE_PRIVATE)
            val bol =sharedPreferences.getBoolean("Loged",false)
        if(bol==true){
            val intent = Intent(this@MainActivity,HomePage::class.java)
            Number=sharedPreferences.getString("number",Number)!!

            intent.putExtra("number",Number)
            startActivity(intent)
            finish()
        }
        etUserNumber=findViewById(R.id.etUserNumber)
        etOtp=findViewById(R.id.etOtp)
        btnGetOtp=findViewById(R.id.btnGetOtp)
        btnSubmitOtp=findViewById(R.id.btnSubmitOtp)
        ccp=findViewById(R.id.ccp)
        btnGetOtp.setOnClickListener {
        //    if (validator.validate()) {
            val num=etUserNumber.text.toString()
            if(num.length!=10){
                Toast.makeText(this@MainActivity,"$num Length should be 10",Toast.LENGTH_LONG).show()
                return@setOnClickListener

            }
            if(!TextUtils.isDigitsOnly(num)){
                Toast.makeText(this@MainActivity,"$num Must Contain Only Digits",Toast.LENGTH_LONG).show()
                return@setOnClickListener

            }
                val number = "+${ccp.selectedCountryCode.toString()}${etUserNumber.text.toString()}"
                Number = number

//                val intent = Intent(this@MainActivity, HomePage::class.java)
//                intent.putExtra("number", Number)
//                startActivity(intent)
                //Remove this
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this@MainActivity,
                phoneAuthCallbacks
            )
//            Toast.makeText(this@MainActivity,"$number",Toast.LENGTH_LONG).show()
//            }
//            else{
//
//            }
        }
        btnSubmitOtp.setOnClickListener {

            val code=etOtp.text.toString()
            if(code.equals("")){
                Toast.makeText(this@MainActivity,"$code Should not be Null",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(code.length!=6){
                Toast.makeText(this@MainActivity,"$code 6 lenght otp",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(!TextUtils.isDigitsOnly(code)){
                Toast.makeText(this@MainActivity,"$code Only Digit are Required",Toast.LENGTH_LONG).show()
                return@setOnClickListener

            }
            VarificationId?.let{
                val credential=PhoneAuthProvider.getCredential(it,code)
                // Toast.makeText(this@MainActivity,"hello $credential",Toast.LENGTH_LONG).show()
                addPhoneNumber(credential)
            }
        }
    }
    val phoneAuthCallbacks= object :PhoneAuthProvider.
    OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential?) {
            phoneAuthCredential?.let{
                addPhoneNumber(it)
            }

        }

        override fun onVerificationFailed(exception: FirebaseException?) {
            //To change body of created functions use File | Settings | File Templates.
            Toast.makeText(this@MainActivity,"${exception?.message!!}",Toast.LENGTH_LONG).show()

        }

        override fun onCodeSent(verifyCode: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(verifyCode, p1)
            VarificationId = verifyCode
          //  Toast.makeText(this@MainActivity, "Varification $VarificationId", Toast.LENGTH_LONG).show()
        }

    }
    fun addPhoneNumber(phoneAuthCredential: PhoneAuthCredential){
        //  Toast.makeText(this@SignUp,"hello$phoneAuthCredential",Toast.LENGTH_LONG).show()
//        val h= FirebaseAuth.getInstance().currentUser
//        if(h==null)
//        {
//            // Toast.makeText(this@SignUp,"hello$h",Toast.LENGTH_LONG).show()
            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).
                addOnCompleteListener(this@MainActivity){
                    if(it.isSuccessful){
                       // Toast.makeText(this@MainActivity, "success", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@MainActivity,HomePage::class.java)
                        intent.putExtra("number",Number)
                        startActivity(intent)
                        finish()
                        sharedPreferences.edit().putString("number",Number).apply()
                        sharedPreferences.edit().putBoolean("Loged",true).apply()


                    }
                    else{

                        Toast.makeText(this@MainActivity, "error $it", Toast.LENGTH_LONG).show()

                    }
                }
//        }
//        else {
//          //  h?.updatePhoneNumber(phoneAuthCredential)?
//            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(this@MainActivity, "success", Toast.LENGTH_LONG).show()
//                    val intent = Intent(this@MainActivity, HomePage::class.java)
//                    startActivity(intent)
//                } else {
//                    Toast.makeText(this@MainActivity, "error $task", Toast.LENGTH_LONG).show()
//                }
//            }
//        }
   }
}
