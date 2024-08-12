package dev.app.cocospring.controller;

import dev.app.cocospring.dto.SelectedCoinPriceDto;
import dev.app.cocospring.service.SelectedCoinPriceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class SelectedCoinPriceController {
    private final SelectedCoinPriceService selectedCoinPriceService;

    public SelectedCoinPriceController(SelectedCoinPriceService selectedCoinPriceService) {
        this.selectedCoinPriceService = selectedCoinPriceService;
    }

    /*
    * 누적된 코인 가격 데이터 호출
    * */
    @GetMapping("/getRecentPriceData")
    public List<SelectedCoinPriceDto> getRecentPriceData(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date lastTimestamp){
        return selectedCoinPriceService.getRecentCoinPriceData(lastTimestamp);
    }



    
    
    
    /*
     * 관심있는 코인 데이터의 가격 정보 저장
     * */
    @PostMapping("/save-price")
    public void saveCoinPrice(@RequestBody SelectedCoinPriceDto selectedCoinPriceDto){
        selectedCoinPriceService.insertSelectedCoinPrice(selectedCoinPriceDto);
    }

}
