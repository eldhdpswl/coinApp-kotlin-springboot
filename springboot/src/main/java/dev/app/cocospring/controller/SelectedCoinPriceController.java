package dev.app.cocospring.controller;

import dev.app.cocospring.Util.DateUtil;
import dev.app.cocospring.dto.SelectedCoinPriceDto;
import dev.app.cocospring.service.SelectedCoinPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class SelectedCoinPriceController {
    private final SelectedCoinPriceService selectedCoinPriceService;

    public SelectedCoinPriceController(SelectedCoinPriceService selectedCoinPriceService) {
        this.selectedCoinPriceService = selectedCoinPriceService;
    }

    /*
    * 누적된 코인 가격 데이터 호출 param lastTimestamp
    * */
    @GetMapping("/getRecentPriceData")
    public List<SelectedCoinPriceDto> getRecentPriceData( @RequestParam String lastTimestamp ){
        log.info("lastTimestamp : " + lastTimestamp);

        Date parsedDate;
        try{
            parsedDate = DateUtil.convertStringToDate(lastTimestamp);
            log.info("parsedDate : " + parsedDate.toString());
        }catch (ParseException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format", e);
        }
        return selectedCoinPriceService.getRecentCoinPriceData(parsedDate);
    }

//    @GetMapping("/getRecentPriceData")
//    public List<SelectedCoinPriceDto> getRecentPriceData(
//            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date lastTimestamp){
//        return selectedCoinPriceService.getRecentCoinPriceData(lastTimestamp);
//    }

    /*
    * 누적된 코인 가격 데이터 호출, 처음 호출할때 사용, 모든 데이터 호출
    * */
    @GetMapping("/getRecentAllPriceData")
    public List<SelectedCoinPriceDto> getRecentAllPriceData(){
        log.info(DateUtil.getCurrentTimestamp() + " getRecentAllPriceData() start");
        return selectedCoinPriceService.getRecentAllPriceData();
    }

    
    
    
    /*
     * 관심있는 코인 데이터의 가격 정보 저장
     * */
//    @PostMapping("/save-price")
//    public void saveCoinPrice(@RequestBody SelectedCoinPriceDto selectedCoinPriceDto){
//        selectedCoinPriceService.insertSelectedCoinPrice(selectedCoinPriceDto);
//    }

}
