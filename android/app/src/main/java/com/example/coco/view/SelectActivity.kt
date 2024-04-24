package com.example.coco.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.Keep
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.coco.background.GetCoinPriceRecentContractedWorkManager
import com.example.coco.view.main.MainActivity
import com.example.coco.databinding.ActivitySelectBinding
import com.example.coco.view.adapter.SelectRVAdapter
import java.util.concurrent.TimeUnit

// https://apidocs.bithumb.com/reference/%ED%98%84%EC%9E%AC%EA%B0%80-%EC%A0%95%EB%B3%B4-%EC%A1%B0%ED%9A%8C-all
class SelectActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySelectBinding

    private val viewModel : SelectViewModel by viewModels()

    private lateinit var selectRVAdapter: SelectRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getCurrentCoinList() // 가공한 데이터 호출
        viewModel.currentPriceResult.observe(this, Observer {

            selectRVAdapter = SelectRVAdapter(this, it)

            // bind
            binding.coinListRV.adapter = selectRVAdapter
            binding.coinListRV.layoutManager = LinearLayoutManager(this)


//            Timber.d(it.toString())
        })
        
        // 처음 접속하는 유저이면 False, 처음접속하는 유저가 아니면 True / True로 바꿔줌
//        viewModel.setUpFirstFlag()

        // DataStore
        // -> Flag ON/OFF 저장할 때 사용
        // -> 처음 가입한 유저인지? / 설정 ON, OFF 설정

        // 선택완료 버튼 클릭 이벤트
        binding.laterTextArea.setOnClickListener{
            
            // 처음 유저인지 아닌지 구분
            viewModel.setUpFirstFlag()

            // 내가 선택한 코인 정보를 넘기기 위해 selectRVAdapter에 있는 좋아요 클릭시 코인리스트를 넘김.
            viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)


        }

        // 저장이 다 되면 메인으로 넘어간다.
        viewModel.save.observe(this, Observer {
            if (it.equals("done")){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                
                
                // 가장 처음으로 우리가 저장한 코인 정보가 저장되는 시점
                saveInterestCoinDataPeriodic()
            }
        })

    }

    // 주기적으로 실행시킨다.
    private fun saveInterestCoinDataPeriodic() {
        val myWork = PeriodicWorkRequest.Builder(
            GetCoinPriceRecentContractedWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).build()

        //유니크한 잡으로 실행한다.
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "GetCoinPriceRecentContractedWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )
    }


}