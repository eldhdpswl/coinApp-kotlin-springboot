package com.example.coco.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coco.R
import com.example.coco.dataModel.CurrentPriceResult
import timber.log.Timber

// 사용자가 스크롤 할 때, 위에 있던 아이템은 재활용 돼서 아래로 이동하여 재사용
class SelectRVAdapter(val context : Context, val coinPriceList : List<CurrentPriceResult>)
    : RecyclerView.Adapter<SelectRVAdapter.ViewHolder>(){

    // 코인 선택, 미선택 시 좋아요 버튼 활용(내가 선택한 코인)
    val selectedCoinList = ArrayList<String>()

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val coinName : TextView = view.findViewById(R.id.coinName)
        val coinPriceUpDown : TextView = view.findViewById(R.id.coinPriceUpDown)
        val likeImage : ImageView = view.findViewById(R.id.likeBtn)
    }

    // 정의된 뷰들을 객체화해서 부모 레이아웃에 전달
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.intro_coin_item, parent, false)
        return ViewHolder(view)
    }

    // ViewHolder를 통해 데이터와 이벤트 연결 등 각종 연산 수행
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.coinName.text = coinPriceList[position].coinName

        val fluctate_24H = coinPriceList[position].coinInfo.fluctate_24H

        if(fluctate_24H.contains("-")) {
            holder.coinPriceUpDown.text = "하락입니다."
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#114fed"))
        } else {
            holder.coinPriceUpDown.text = "상승입니다."
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#ed2e11"))
        }

        val likeImage = holder.likeImage
        val currentCoin = coinPriceList[position].coinName  // 현재 클릭된 코인

        // view 를 그려줄 때 --> recycle은 view를 재활용되기 때문에 스크롤을 내리다보면 선택하지 않는 코인도 좋아요 클릭 형태로 나옴
        if (selectedCoinList.contains(currentCoin)) {
            likeImage.setImageResource(R.drawable.like_red)
        } else {
            likeImage.setImageResource(R.drawable.like_grey)
        }
    
        // 좋아요 버튼 클릭시
        likeImage.setOnClickListener {
            Timber.d(currentCoin)

            if (selectedCoinList.contains(currentCoin)) {
                // 포함하면 (좋아요 버튼 취소할떄)
                selectedCoinList.remove(currentCoin)
                likeImage.setImageResource(R.drawable.like_grey)
            } else {
                // 포함하지 않으면 (좋아요 버튼 클릭할떄)
                selectedCoinList.add(currentCoin)
                likeImage.setImageResource(R.drawable.like_red)
            }

            Timber.d(selectedCoinList.toString())


        }

    }

    // 아이템의 크기를 반환
    override fun getItemCount(): Int {
        return coinPriceList.size
    }


}