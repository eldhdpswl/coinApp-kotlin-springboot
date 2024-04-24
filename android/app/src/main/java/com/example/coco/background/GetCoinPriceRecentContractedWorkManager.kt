package com.example.coco.background

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.coco.dataModel.SelectedCoinPriceDto
import com.example.coco.db.entity.SelectedCoinPriceEntity
import com.example.coco.network.model.RecentCoinPriceList
import com.example.coco.repository.DBExternalRepository
import com.example.coco.repository.DBRepository
import com.example.coco.repository.NetworkRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


// 최근 거래된 코인 가격 내역을 가져오는 WorkManager

// 1. 저희가 관심있어하는 코인 리스트를 가져와서
// 2. 관심있는 코인 각각의 가격 변동 정보를 가져와서 (New API)
// 3. 관심있는 코인 각각의 가격 변동 정보 DB에 저장

class GetCoinPriceRecentContractedWorkManager(val context : Context, workerParameters: WorkerParameters)
    : CoroutineWorker(context, workerParameters){

    private val dbRepository = DBRepository()
    private val netWorkRepository = NetworkRepository()

    // SpringBoot + MySQL 사용할때
    private val dbExternalRepository = DBExternalRepository()

    override suspend fun doWork(): Result {

        Timber.d("doWork")

        getAllInterestSelectedCoinData()

        return Result.success()

    }


    // 1. 저희가 관심있어하는 코인 리스트를 가져와서
    suspend fun getAllInterestSelectedCoinData() {
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        val timeStamp = Calendar.getInstance().time
//        val time = Calendar.getInstance().time
//        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val timeString = formatter.format(time)
//        val timeStamp = formatter.parse(timeString)
//        val timeStamp = time.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS"))
//        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS")
//        val timeStamp = LocalDateTime.now().format(formatter)


        for(coinData in selectedCoinList) {
//            Timber.d(coinData.toString())

            // 2. 관심있는 코인 각각의 가격 변동 정보를 가져와서 (New API)
            val recentCoinPriceList = netWorkRepository.getInterestCoinPriceData(coinData.coin_name)

            Timber.d(recentCoinPriceList.toString())

            saveSelectedCoinPrice(
                coinData.coin_name,
                recentCoinPriceList,
                timeStamp
            )

        }
    }

    // 3. 관심있는 코인 각각의 가격 변동 정보 DB에 저장
    fun saveSelectedCoinPrice(
        coinName : String,
        recentCoinPriceList: RecentCoinPriceList,
        timeStamp : Date
    ){

//        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        // roomDB 사용할때
        // data[0]인 이유는 첫번째 데이터만 가져와서 쓰겠다
        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timeStamp

        )


        // SpringBoot + MySQL 사용할때
        var call: Call<ResponseBody>


//        val formatter = SimpleDateFormat("MM.dd.yyyy")
//        val datetimestamp = formatter.parse(dateString)

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val timeStampString = formatter.format(timeStamp)

        val selectedCoinPriceDto = SelectedCoinPriceDto(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timeStampString
//            datetimestamp

        )


        // roomDB 사용할때
        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)

        // SpringBoot + MySQL 사용할때
        call = dbExternalRepository.insertSelectedCoinPrice(selectedCoinPriceDto)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Timber.d("Succeed : $response")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Timber.d("Failed : $t")
            }

        })

    }


}
