package com.example.coco.repository

import com.example.coco.network.Api
import com.example.coco.network.RetrofitInstance

//레파지토리에서 Api를 호출해서 사용할 것
// 관리하기 편해서 레파지토리라는 거를 만들어서 데이터를 관리하는것
class NetworkRepository {

    private val client = RetrofitInstance.getInstance().create(Api::class.java)

    suspend fun getCurrentCoinList() = client.getCurrentCoinList()

    suspend fun getInterestCoinPriceData(coin : String) = client.getRecentCoinPrice(coin)

}