package com.example.coco.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coco.db.entity.SelectedCoinPriceEntity

@Dao
interface SelectedCoinPriceDAO {

    // 코인 가격 데이터의 최신 timestamp 가져오기
    @Query("SELECT * FROM selected_coin_price_table ORDER BY timeStamp DESC LIMIT 1")
    fun getLastData() : SelectedCoinPriceEntity?

    // getAllData
    @Query("SELECT * FROM selected_coin_price_table")
    fun getAllData() : List<SelectedCoinPriceEntity>

    // insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(selectedCoinPriceEntity: SelectedCoinPriceEntity)


    // 하나의 코인에 대해서 저장된 정보를 가져오는 친구
    // BTC 15 30 45 -> List<BTC> -> 현재가격 15 30 45분 전에 어떻게 변화했는지 DB에 저장된 값과 비교하는 용도
    @Query("SELECT * FROM selected_coin_price_table WHERE coinName = :coinName")
    fun getOneCoinData(coinName : String) : List<SelectedCoinPriceEntity>


}