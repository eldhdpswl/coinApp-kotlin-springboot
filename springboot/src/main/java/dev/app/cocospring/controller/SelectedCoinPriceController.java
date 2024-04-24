package dev.app.cocospring.controller;

import dev.app.cocospring.dto.SelectedCoinPriceDto;
import dev.app.cocospring.service.SelectedCoinPriceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SelectedCoinPriceController {
    private final SelectedCoinPriceService selectedCoinPriceService;

    public SelectedCoinPriceController(SelectedCoinPriceService selectedCoinPriceService) {
        this.selectedCoinPriceService = selectedCoinPriceService;
    }

    /*
     * 관심있는 코인 데이터의 가격 정보 저장
     * */
    @PostMapping("/save-price")
    public void saveCoinPrice(@RequestBody SelectedCoinPriceDto selectedCoinPriceDto){
        selectedCoinPriceService.insertSelectedCoinPrice(selectedCoinPriceDto);
    }

}
