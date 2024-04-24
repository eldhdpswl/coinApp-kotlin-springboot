package com.example.coco.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.coco.db.entity.InterestCoinEntity
import kotlinx.coroutines.flow.Flow


// 쿼리 부분
@Dao
interface InterestCoinDAO {

    // getAllData
    // https://medium.com/androiddevelopers/room-flow-273acffe5b57
    // FLow를 사용한 것은 데이터의 변경 사항을 감지하기 좋다 -> DB의 데이터 변경사항을 감지하기 좋다.
    // 변경사항을 감지하지 못하면 일일이 데이터 변경할때마다 뷰를 업데이트하는 코드를 작성해야한다.
    @Query("SELECT * FROM interest_coin_table")
    fun getAllData() : Flow<List<InterestCoinEntity>>

    //Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(interestCoinEntity: InterestCoinEntity)

    // update
    // 사용자가 코인 데이터를 선택했다가 다시 취소할 수도 있고, 반대로 선택안된 것을 선택할 수도 있게 함
    @Update
    fun update(interestCoinEntity: InterestCoinEntity)

    // getSelectedCoinList -> 내가 관심있어한 코인 데이터를 가져오는 것
    // coin1 / coin2 / coin3 -> coin1 data / coin2 data / coin3 data
    @Query("SELECT * FROM interest_coin_table WHERE selected =  :selected")
    fun getSelectedData(selected : Boolean = true) : List<InterestCoinEntity>


}