package com.example.intershala.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataCarrier(
     var txtPropertyName:String,
    var txtPropertyLocation:String,
     var txtLocality:String,
    var txtOwnerNumber:String,
     var txtOwnerName:String,
     var txtLanguage:String,
     var txtApplicationStatus:String,
     var txtUserNumber:String

):Parcelable