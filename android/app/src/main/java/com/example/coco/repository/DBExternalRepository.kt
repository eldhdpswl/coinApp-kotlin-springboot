package com.example.coco.repository

import com.example.coco.dataModel.InterestCoinDto
import com.example.coco.dataModel.SelectedCoinPriceDto
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.db.entity.SelectedCoinPriceEntity
import com.example.coco.network.RetrofitClient
import com.example.coco.network.SpringApi

class DBExternalRepository {
    private val client = RetrofitClient.getInstance().create(SpringApi::class.java)

    // 전체 코인 데이터 가져오기
//    suspend fun getAllInterestCoinData() = client.getAllInterestCoinData()
    suspend fun getAllInterestCoinData() = client.getAllInterestCoinData()

    // 코인 데이터 넣기
    fun insertInterestCoinData(interestCoinDto: InterestCoinDto) = client.insertInterestCoinData(interestCoinDto)

    // 코인 데이터 업데이트
//    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = db.interestCoinDAO().update(interestCoinEntity)
    fun updateInterestCoinData(interestCoinDto: InterestCoinDto) = client.updateInterestCoinData(interestCoinDto)
    
    // 코인 가격 데이터 저장하기
    fun insertSelectedCoinPrice(selectedCoinPriceDto: SelectedCoinPriceDto) = client.insertSelectedCoinPrice(selectedCoinPriceDto)


    // 사용자가 관심있어한 코인만 가져오기
//    fun getAllInterestSelectedCoinData() = db.interestCoinDAO().getSelectedData()

    // 사용자가 관심있어하는 코인 가격 데이터 저장
//    fun insertCoinPriceData(selectedCoinPriceEntity: SelectedCoinPriceEntity) = db.selectedCoinDAO().insert(selectedCoinPriceEntity)

//    suspend fun getCurrentCoinList() = client.getCurrentCoinList()
//    suspend fun getInterestCoinPriceData(coin : String) = client.getRecentCoinPrice(coin)
    
    
}