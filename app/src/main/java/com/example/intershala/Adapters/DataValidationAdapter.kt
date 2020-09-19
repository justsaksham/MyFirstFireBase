package com.example.intershala.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.intershala.ChangeStatus
import com.example.intershala.Model.DataCarrier
import com.example.intershala.Model.Userimfo
import com.example.intershala.R
import kotlin.concurrent.fixedRateTimer

class DataValidationAdapter(val context: Context, val list:ArrayList<Userimfo> ):RecyclerView.Adapter<DataValidationAdapter.DataValidationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataValidationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view,parent,false)
        return DataValidationViewHolder(view)
    }

    override fun getItemCount(): Int {
        //To change body of created functions use File | Settings | File Templates.
        return list.size
    }

    override fun onBindViewHolder(holder: DataValidationViewHolder, position: Int) {
        val userimfo=list[position]
        holder.txtPropertyName.text =userimfo.property.toString()
        holder.txtOwnerName.text = userimfo.owenerName.toString()
        if(userimfo.preferredLanguage.equals("")){
            holder.txtLanguage.text = userimfo.preferredLanguage.toString()
        }
       else holder.txtLanguage.text = "N/A"
        holder.txtLocality.text =  userimfo.area.toString()
        holder.txtPropertyLocation.text= userimfo.location.toString()
        if(userimfo.owenerName.equals("")){
            holder.txtOwnerNumber.text= "N/A"
        }
       else holder.txtOwnerNumber.text = userimfo.clientNumber
        holder.txtApplicationStatus.text = userimfo.status
        holder.OwnerRecyclerView.setOnClickListener{
           // Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show()
            val userinfo=DataCarrier(userimfo.property!!,userimfo.location!!,userimfo.area!!,
                userimfo.clientNumber!!,userimfo.owenerName!!,userimfo.preferredLanguage!!,userimfo.status!!,userimfo.userNumber)

            val intent = Intent(context,ChangeStatus::class.java)
            intent.putExtra("userimfo",userinfo)
            context.startActivity(intent)
            //val intent = Intent(context, FoodItemList::class.java)
        }
        }


    class DataValidationViewHolder(view : View): RecyclerView.ViewHolder(view){
        val OwnerRecyclerView:LinearLayout= view.findViewById(R.id.OwnerRecyclerView)
       val txtPropertyName:TextView=view.findViewById(R.id.txtPropertyName)
        val txtPropertyLocation:TextView=view.findViewById(R.id.txtPropertyLocation)
        val txtLocality:TextView=view.findViewById(R.id.txtLocality)
        val txtOwnerNumber:TextView=view.findViewById(R.id.txtOwnerNumber)
        val txtOwnerName:TextView=view.findViewById(R.id.txtOwnerName)
        val txtLanguage:TextView=view.findViewById(R.id.txtLanguage)
        val txtApplicationStatus:TextView=view.findViewById(R.id.txtApplicationStatus)


    }
}