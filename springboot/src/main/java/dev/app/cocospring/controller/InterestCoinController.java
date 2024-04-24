package dev.app.cocospring.controller;

import dev.app.cocospring.dto.InterestCoinDto;
import dev.app.cocospring.service.CoinInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InterestCoinController {

    private final CoinInfoService coinInfoService;

    public InterestCoinController(CoinInfoService coinInfoService) {
        this.coinInfoService = coinInfoService;
    }

    /*
    * 빗썸 Open API를 통해 받아온 코인 데이터 저장
    * */
    @PostMapping("/save-coin")
//    @ResponseBody
    public void saveCoinInfo(@RequestBody InterestCoinDto interestCoinDto){
        coinInfoService.insertCoinInfo(interestCoinDto);
    }

    /*
    * 코인 데이터 모두 불러오기
    * */
    @GetMapping("/getAll-coin")
    public List<InterestCoinDto> getCoinInfoAll(){
        return coinInfoService.getAllInterestCoinData();
    }

    /*
    * 관심있는 코인 데이터 선택/취소
    * */
    @PutMapping("/update-coin")
//    @ResponseBody
    public void putCoinInfo(@RequestBody InterestCoinDto interestCoinDto){
        coinInfoService.updateInterestCoinData(interestCoinDto);
    }

    /*
    * 관심있는 코인 데이터 모두 불러오기
    * */
    @GetMapping("/getSelectedAll-coin")
    public List<InterestCoinDto> getSelectedCoinInfoAll(){
        return coinInfoService.getAllInterestSelectedCoinData();
    }


}
