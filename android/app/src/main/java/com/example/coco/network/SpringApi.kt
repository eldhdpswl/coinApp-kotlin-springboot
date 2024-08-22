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
import retrofit2.http.Query
import retrofit2.http.Path

interface SpringApi {

    /*
    * SpingBoot + MySQL 서버에 있는 데이터 모두 불러오기
    * */
    @GET("getAll-coin")
    suspend fun getAllInterestCoinData() : List<InterestCoinDto>

    /*
    * SpingBoot + MySQL 서버에 있는 데이터 selected 컬럼 update
    * */
    @PUT("update-coin")
    fun updateInterestCoinData(@Body interestCoinDto: InterestCoinDto) : Call<ResponseBody>

    /*
    * SpingBoot + MySQL 서버에 있는 누적된 코인 가격 데이터 호출, param lastTimestamp
    * */
    @GET("getRecentPriceData")
    suspend fun getRecentPriceData(@Query("lastTimestamp") lastTimestamp: String) : List<SelectedCoinPriceDto>

    /*
    * SpingBoot + MySQL 서버에 있는 누적된 코인 가격 데이터 호출, 처음 호출할때 사용, 모든 데이터 호출
    * */
    @GET("getRecentAllPriceData")
    suspend fun getRecentPriceAllData() : List<SelectedCoinPriceDto>






//    @POST("/save-coin")
//    fun insertInterestCoinData(@Body interestCoinDto: InterestCoinDto) : Call<ResponseBody>
//    fun insertInterestCoinData(@Body interestCoinDto: InterestCoinDto?) : Call<ResponseBody?>?



//    @POST("/save-price")
//    fun insertSelectedCoinPrice(@Body selectedCoinPriceDto: SelectedCoinPriceDto) : Call<ResponseBody>


    // getCurerentCoinList를 repository에서 Api 호출을 관리할라고 한다.
//    @GET("public/ticker/ALL_KRW")
//    suspend fun getCurrentCoinList() : CurrentPriceList
//
//    @GET("public/transaction_history/{coin}_KRW")
//    suspend fun getRecentCoinPrice(@Path("coin") coin : String) : RecentCoinPriceList

}