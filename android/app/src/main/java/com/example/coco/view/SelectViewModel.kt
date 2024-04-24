package com.example.coco.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coco.dataModel.CurrentPrice
import com.example.coco.dataModel.CurrentPriceResult
import com.example.coco.dataModel.InterestCoinDto
import com.example.coco.dataStore.MyDataStore
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.repository.DBExternalRepository
import com.example.coco.repository.DBRepository
import com.example.coco.repository.NetworkRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class SelectViewModel : ViewModel() {

    private val netWorkRepository = NetworkRepository()
    private val dbRepository = DBRepository()

    // SpringBoot + MySQL 사용할때
    private val dbExternalRepository = DBExternalRepository()

    // 전체 코인데이터
    private lateinit var currentPriceResultList : ArrayList<CurrentPriceResult>

    // 데이터변화를 관찰 LiveData
    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult : LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    // 코인이 다 저장되었는지 아닌지 구분하는 것
    private val _saved = MutableLiveData<String>()
    val save : LiveData<String>
        get() = _saved


    // 데이터 가공 작업
    fun getCurrentCoinList() = viewModelScope.launch {

        val result = netWorkRepository.getCurrentCoinList()

        currentPriceResultList = ArrayList()

        for(coin in result.data) {

            // 원하는 데이터 포맷이 아닐때를 해결하기 위해 예외처리
            try{
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)

                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)

                currentPriceResultList.add(currentPriceResult)

            }catch (e : java.lang.Exception) {
                Timber.d(e.toString())
            }

        }

        _currentPriceResult.value = currentPriceResultList

    }

    fun setUpFirstFlag() = viewModelScope.launch {
        MyDataStore().setupFirstData()
    }

    // DB에 데이터 저장
    // https://developer.android.com/kotlin/coroutines/coroutines-adv?hl=ko
    // roomDB를 사용할때 Dispatchers.IO를 사용하는게 낫다.
    fun saveSelectedCoinList(selectedCoinList: ArrayList<String>) = viewModelScope.launch (Dispatchers.IO) {

        // SpringBoot + MySQL 사용할때
        var call: Call<ResponseBody>

        // 1. 전체 코인 데이터를 가져와서
        for(coin in currentPriceResultList) {
            Timber.d(coin.toString())

            // 2. 내가 선택한 코인인지 아닌지 구분해서
            // 포함하면 TRUE / 포함하지 않으면 FALSE
            val selected = selectedCoinList.contains(coin.coinName)

            // 로컬db에 저장  roomDB 사용할때
            val interestCoinEntity = InterestCoinEntity(
                0,
                coin.coinName,
                coin.coinInfo.opening_price,
                coin.coinInfo.closing_price,
                coin.coinInfo.min_price,
                coin.coinInfo.max_price,
                coin.coinInfo.units_traded,
                coin.coinInfo.acc_trade_value,
                coin.coinInfo.prev_closing_price,
                coin.coinInfo.units_traded_24H,
                coin.coinInfo.acc_trade_value_24H,
                coin.coinInfo.fluctate_24H,
                coin.coinInfo.fluctate_rate_24H,
                selected
            )

            // 3. 저장  roomDB 사용할때
            interestCoinEntity.let {
                dbRepository.insertInterestCoinData(it)
            }
            
            // Dto에 담기  SpringBoot + MySQL 사용할때
            val interestCoinDto = InterestCoinDto(
                0,
                coin.coinName,
                coin.coinInfo.opening_price,
                coin.coinInfo.closing_price,
                coin.coinInfo.min_price,
                coin.coinInfo.max_price,
                coin.coinInfo.units_traded,
                coin.coinInfo.acc_trade_value,
                coin.coinInfo.prev_closing_price,
                coin.coinInfo.units_traded_24H,
                coin.coinInfo.acc_trade_value_24H,
                coin.coinInfo.fluctate_24H,
                coin.coinInfo.fluctate_rate_24H,
                selected
            )

            // 3. 저장   SpringBoot + MySQL 사용할때
            interestCoinDto.let {
                call = dbExternalRepository.insertInterestCoinData(it)
                call.enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Timber.d("Succeed : $response")
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Timber.d("Failed : $t")
                    }

                })

            }

        }

        // 쓰레드 변경
        withContext(Dispatchers.Main){
            _saved.value = "done"
        }



    }


}