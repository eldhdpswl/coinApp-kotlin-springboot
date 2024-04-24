package com.example.coco.network

import androidx.lifecycle.LiveData
import com.example.coco.dataModel.InterestCoinDto
import com.example.coco.dataModel.SelectedCoinPriceDto
import com.example.coco.network.model.CurrentPriceList
import com.example.coco.network.model.RecentCoinPriceList
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SpringApi {

    @GET("/getAll-coin")
//    fun getAllInterestCoinData() : LiveData<List<InterestCoinDto>>
    suspend fun getAllInterestCoinData() : LiveData<List<InterestCoinDto>>


    @POST("/save-coin")
    fun insertInterestCoinData(@Body interestCoinDto: InterestCoinDto) : Call<ResponseBody>
//    fun insertInterestCoinData(@Body interestCoinDto: InterestCoinDto?) : Call<ResponseBody?>?

    @PUT("/update-coin")
    fun updateInterestCoinData(@Body interestCoinDto: InterestCoinDto) : Call<ResponseBody>


    @POST("/save-price")
    fun insertSelectedCoinPrice(@Body selectedCoinPriceDto: SelectedCoinPriceDto) : Call<ResponseBody>


    // getCurerentCoinList를 repository에서 Api 호출을 관리할라고 한다.
//    @GET("public/ticker/ALL_KRW")
//    suspend fun getCurrentCoinList() : CurrentPriceList
//
//    @GET("public/transaction_history/{coin}_KRW")
//    suspend fun getRecentCoinPrice(@Path("coin") coin : String) : RecentCoinPriceList

}