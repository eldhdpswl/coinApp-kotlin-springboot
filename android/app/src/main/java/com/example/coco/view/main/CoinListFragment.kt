package com.example.coco.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coco.dataModel.InterestCoinDto
import com.example.coco.databinding.FragmentCoinListBinding
import com.example.coco.db.entity.InterestCoinEntity
import com.example.coco.view.adapter.CoinListRVAdapter
import timber.log.Timber

class CoinListFragment : Fragment() {

    private  val viewModel : MainViewModel by activityViewModels()

    private var _binding : FragmentCoinListBinding? = null
    private val binding get() = _binding!!

    // RoomDB 사용할때
    private val selectedList = ArrayList<InterestCoinEntity>()
    private val unSelectedList = ArrayList<InterestCoinEntity>()

    // SpringBoot + MySQL 사용할때
//    private val selectedList = ArrayList<InterestCoinDto>()
//    private val unSelectedList = ArrayList<InterestCoinDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        val view = binding.root

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllInterestCoinData()
        viewModel.selectCoinList.observe(viewLifecycleOwner, Observer {

            // 라이브 데이터이기 때문에 리스트에 계속 쌓인다. 그래서 clear
            selectedList.clear()
            unSelectedList.clear()

            for(item in it) {
                if(item.selected) {
                    selectedList.add(item)
                } else {
                    unSelectedList.add(item)
                }
            }

//            Timber.d(selectedList.toString())
//            Timber.d(unSelectedList.toString())

            setSelectedListRv()

        })

    }

    private fun setSelectedListRv(){

        val selectedRvAdapter = CoinListRVAdapter(requireContext(), selectedList)
        binding.selectedCoinRV.adapter = selectedRvAdapter
        binding.selectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        selectedRvAdapter.itemClick = object : CoinListRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                viewModel.updateInterestCoinData(selectedList[position])

            }

        }

        val unSelectedRvAdapter = CoinListRVAdapter(requireContext(), unSelectedList)
        binding.unSelectedCoinRV.adapter = unSelectedRvAdapter
        binding.unSelectedCoinRV.layoutManager = LinearLayoutManager(requireContext())

        unSelectedRvAdapter.itemClick = object : CoinListRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                viewModel.updateInterestCoinData(unSelectedList[position])

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}