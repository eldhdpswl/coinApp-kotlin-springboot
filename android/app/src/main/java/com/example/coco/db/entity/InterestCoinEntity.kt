package com.example.coco.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.coco.dataModel.InterestCoinDto

@Entity(tableName = "interest_coin_table")
data class InterestCoinEntity (

    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val coin_name : String,
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
    val fluctate_rate_24H : String,
    var selected : Boolean


){
    // InterestCoinDto로의 변환 함수 추가
    fun toDto(): InterestCoinDto {
        return InterestCoinDto(
            id = this.id,
            coin_name = this.coin_name,
            opening_price = this.opening_price,
            closing_price = this.closing_price,
            min_price = this.min_price,
            max_price = this.max_price,
            units_traded = this.units_traded,
            acc_trade_value = this.acc_trade_value,
            prev_closing_price = this.prev_closing_price,
            units_traded_24H = this.units_traded_24H,
            acc_trade_value_24H = this.acc_trade_value_24H,
            fluctate_24H = this.fluctate_24H,
            fluctate_rate_24H = this.fluctate_rate_24H,
            selected = this.selected
        )
    }
}