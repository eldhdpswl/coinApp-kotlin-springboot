package com.example.coco.dataModel

import androidx.room.TypeConverter
import java.util.Date

data class SelectedCoinPriceDto(

    val id : Int,
    val coinName : String,
    val transaction_date : String,
    val type : String,
    val units_traded : String,
    val price : String,
    val total : String,
    val timeStamp : String
//    val timeStamp : Date



)
