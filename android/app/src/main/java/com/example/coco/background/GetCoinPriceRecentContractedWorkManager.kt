package com.example.coco.background

import android.content.Context
import android.os.Build
import android.util.Log
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
import java.util.Locale


// 최근 거래된 코인 가격 내역을 가져오는 WorkManager

// 수정전
// 1. 저희가 관심있어하는 코인 리스트를 가져와서
// 2. 관심있는 코인 각각의 가격 변동 정보를 가져와서 (New API)
// 3. 관심있는 코인 각각의 가격 변동 정보 DB에 저장

// 수정후
// 코인 가격 최신 데이터 가져오기
// 관심있는 코인 각각의 가격 변동 정보 DB에 저장

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

    // 코인 가격 최신 데이터 가져오기
    suspend fun getAllInterestSelectedCoinData() {

        Timber.d("getAllInterestSelectedCoinData() start")

        // 코인 가격 최신 데이터 가져오기
        val coinPriceLastData = dbRepository.getLastCoinPriceData()
        val lastTimeStamp = coinPriceLastData?.timeStamp

        Timber.d("getAllInterestSelectedCoinData() lastTimeStamp : %s", lastTimeStamp)

        val selectedCoinPriceList: List<SelectedCoinPriceDto> = if (lastTimeStamp != null) {
            val lastTimeStampString = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastTimeStamp)
            Timber.d("getAllInterestSelectedCoinData() - getRecentPriceData(lastTimeStampString) start")
            dbExternalRepository.getRecentPriceData(lastTimeStampString)

        } else {
            Timber.d("getAllInterestSelectedCoinData() - getRecentPriceAllData() start")
            dbExternalRepository.getRecentPriceAllData()
        }

        Timber.d("selectedCoinPriceList count : %d", selectedCoinPriceList.size)


//        초기화까지 해야되서 불필요한 소스가 추가된다.
//        val selectedCoinPriceList: List<SelectedCoinPriceDto>
//        if (lastTimeStamp != null) {
//            val lastTimeStampString = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastTimeStamp)
//            selectedCoinPriceList = dbExternalRepository.getRecentPriceData(lastTimeStampString)
//
//            Log.d("getAllInterestSelectedCoinData()", "getRecentPriceData(lastTimeStampString) start")
//
//        } else {
//            selectedCoinPriceList = dbExternalRepository.getRecentPriceAllData()
//
//            Log.d("getAllInterestSelectedCoinData()", "getRecentPriceAllData() start")
//        }


        selectedCoinPriceList.forEach {coinData ->
            saveSelectedCoinPrice(coinData)

        }

    }

    // 관심있는 코인 각각의 가격 변동 정보 DB에 저장
    fun saveSelectedCoinPrice(coinData : SelectedCoinPriceDto) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//        val parsedDate: Date? = dateFormat.parse(coinData.timeStamp)
        val parsedDate: Date = dateFormat.parse(coinData.timeStamp)

        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            coinData.id,
            coinData.coinName,
            coinData.transaction_date,
            coinData.type,
            coinData.units_traded,
            coinData.price,
            coinData.total,
            parsedDate

        )

        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)

    }

}
