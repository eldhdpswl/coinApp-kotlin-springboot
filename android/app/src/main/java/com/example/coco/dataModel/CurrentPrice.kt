package com.example.coco.dataModel

// 데이터 가공하기 위해
data class CurrentPrice (

    val opening_price : String,
    val closing_price : String,
    val min_price : String,
    val max_price : String,
    val units_traded : String,
    val acc_trade_value : String,
    val prev_closing_price : String,
    val units_traded_24H : String,
    val acc_trade_value_24H : String,
    val fluctate_24H : String,
    val fluctate_rate_24H : String

)