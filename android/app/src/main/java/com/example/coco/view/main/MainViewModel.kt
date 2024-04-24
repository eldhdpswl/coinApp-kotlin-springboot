package com.example.coco.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.coco.dataModel.InterestCoinDto
import com.example.coco.dataModel.UpDownDataSet
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.repository.DBExternalRepository
import com.example.coco.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel : ViewModel() {

    private val dbRepository = DBRepository()
    private val dbExternalRepository = DBExternalRepository()

    // roomDB 사용할때
    lateinit var selectCoinList: LiveData<List<InterestCoinEntity>>


    // SpringBoot + MySQL 사용할때
//    lateinit var selectCoinList: LiveData<List<InterestCoinDto>>

//    private val _selectCoinList = MutableLiveData<List<InterestCoinDto>>()
//    var selectCoinList: LiveData<List<InterestCoinDto>> = _selectCoinList


    private val _arr15min = MutableLiveData<List<UpDownDataSet>>()
    val arr15min : LiveData<List<UpDownDataSet>>
        get() = _arr15min

    private val _arr30min = MutableLiveData<List<UpDownDataSet>>()
    val arr30min : LiveData<List<UpDownDataSet>>
        get() = _arr30min

    private val _arr45min = MutableLiveData<List<UpDownDataSet>>()
    val arr45min : LiveData<List<UpDownDataSet>>
        get() = _arr45min


    // CoinListFragment

    fun getAllInterestCoinData() = viewModelScope.launch {

        // roomDB 사용할때
        val coinList = dbRepository.getAllInterestCoinData().asLiveData()

        // SpringBoot + MySQL 사용할때
//        val coinList = dbExternalRepository.getAllInterestCoinData().asLiveData()
//        val coinList = dbExternalRepository.getAllInterestCoinData()
        selectCoinList = coinList


    }

    // 관심있는 코인 선택 해제 기능 (RoomDB 사용할때) + (SpringBoot + MySQL 사용할때)
    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = viewModelScope.launch(Dispatchers.IO) {

        // RoomDB 사용할때
        if(interestCoinEntity.selected) {
            interestCoinEntity.selected = false
        } else {
            interestCoinEntity.selected = true
        }
//        Timber.d("interestCoinEntity.selected : ", interestCoinEntity.selected.toString())

        dbRepository.updateInterestCoinData(interestCoinEntity)

        // SpringBoot + MySQL 사용할때
        var call: Call<ResponseBody>

        val interestCoinDto = interestCoinEntity.toDto()

        call = dbExternalRepository.updateInterestCoinData(interestCoinDto)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Timber.d("Succeed : $response")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Timber.d("Failed : $t")
            }

        })

    }

    // 관심있는 코인 선택 해제 기능 (SpringBoot + MySQL 사용할때)
//    fun updateInterestCoinDataExternel(interestCoinDto: InterestCoinDto) = viewModelScope.launch(Dispatchers.IO) {
//
//        var call: Call<ResponseBody>
//
//        if(interestCoinDto.selected) {
//            interestCoinDto.selected = false
//        } else {
//            interestCoinDto.selected = true
//        }
//
//        dbExternalRepository.updateInterestCoinData(interestCoinDto)
//
//    }


    // PriceChangeFragment
    // 1. 관심있다고 선택한 코인 리스트를 가져와서
    // 2. 관심있다고 선택한 코인 리스트를 반복문을 통해 하나씩 가져와서
    // 3. 저장된 코인 가격 리스트를 가져와서
    // 4. 시간대마다 어떻게 변경되었는지를 알려주는 로직을 작성

    fun getAllSelectedCoinData() = viewModelScope.launch(Dispatchers.IO) {

        // 1. 저희가 관심있다고 선택한 코인 리스트를 가져와서
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        val arr15min = ArrayList<UpDownDataSet>()
        val arr30min = ArrayList<UpDownDataSet>()
        val arr45min = ArrayList<UpDownDataSet>()

        // 2. 관심있다고 선택한 코인 리스트를 반복문을 통해 하나씩 가져와서
        for(data in selectedCoinList) {

            // 3. 저장된 코인 가격 리스트를 가져와서
            val coinName = data.coin_name  // ex) coinName = BTC
            val oneCoinData = dbRepository.getOneSelectedCoinData(coinName).reversed() // 가장 마지막 값이 최신이어서 거꾸로 가져온다.

            //  [BTC15, BTC30, BTC45 ]

            // [0 1 2 3 4] -> 가장 마지막에 저장된 값이 최신 값

            val size = oneCoinData.size

            if(size > 1) {
                // DB에 값이 2개 이상은 있다.
                // 현재와 15분전 가격을 비교하려면 데이터가 2개는 있어야한다.
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[1].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr15min.add(upDownDataSet)

            }

            if(size > 2) {
                // DB에 값이 3개 이상은 있다.
                // 현재와 30분전 가격을 비교하려면 데이터가 3개는 있어야한다.
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[2].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr30min.add(upDownDataSet)

            }

            if(size > 3) {
                // DB에 값이 4개 이상은 있다.
                // 현재와 45분전 가격을 비교하려면 데이터가 4개는 있어야한다.
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[3].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr45min.add(upDownDataSet)

            }

        }

        // 쓰레드를 변경해준다.
        withContext(Dispatchers.Main) {
            _arr15min.value = arr15min
            _arr30min.value = arr30min
            _arr45min.value = arr45min
        }



    }
    

}