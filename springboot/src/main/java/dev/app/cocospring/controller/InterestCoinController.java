package dev.app.cocospring.controller;

import dev.app.cocospring.dto.GetCoinInfo;
import dev.app.cocospring.dto.InterestCoinDto;
import dev.app.cocospring.service.CoinInfoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class InterestCoinController {

    private final CoinInfoService coinInfoService;

    public InterestCoinController(CoinInfoService coinInfoService) {
        this.coinInfoService = coinInfoService;
    }

    /*
     * WebClient 사용, 전체데이터 호출(테스트용)
     * */
    @GetMapping("/webclient-test")
    @ResponseBody
    public Mono<GetCoinInfo> savetest(){
        return coinInfoService.getCoindData();
    }

    /*
     * WebClient 사용 post 전체데이터 호출하고 테이블에 저장(수정필요)
     * */
    @PostMapping("/webclient-saveCoinInfo")
    public Mono<Void> saveCoinInfo(){
        return coinInfoService.saveCoinDate();
    }


    /*
     * WebClient selected 코인 최신 거래 데이터 저장(테스트용)
     * */
    @PostMapping("/webclient-testSelectedPost/{coinName}")
    public Mono<Void> selectedCoinInfo(@PathVariable String coinName){
        return coinInfoService.selectedCoinData(coinName);
    }

    /*
    * WebClient selected 코인 전체 데이터 최근 거래 내역 저장
    * */
    @PostMapping("/webclient-saveSelectedCoinPrice")
    public Mono<Void> saveSelectedCoinPrice(){
        return coinInfoService.saveSelectedCoinData();
    }


    /*
     * 관심있는 코인 데이터 모두 불러오기
     * */
    @GetMapping("/getSelectedAll-coin")
    public List<InterestCoinDto> getSelectedCoinInfoAll(){
        return coinInfoService.getAllInterestSelectedCoinData();
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
     * 빗썸 Open API를 통해 받아온 코인 데이터 저장
     * */
//    @PostMapping("/save-coin")
////    @ResponseBody
//    public void saveCoinInfo(@RequestBody InterestCoinDto interestCoinDto){
//        coinInfoService.insertCoinInfo(interestCoinDto);
//    }

    /*
     * 코인 데이터 모두 불러오기
     * */
    @GetMapping("/getAll-coin")
    public List<InterestCoinDto> getCoinInfoAll(){
        return coinInfoService.getAllInterestCoinData();
    }






}
