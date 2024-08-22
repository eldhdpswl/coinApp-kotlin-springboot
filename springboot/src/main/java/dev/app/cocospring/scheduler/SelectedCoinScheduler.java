package dev.app.cocospring.scheduler;

import dev.app.cocospring.Util.DateUtil;
import dev.app.cocospring.service.CoinInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SelectedCoinScheduler {
    private final CoinInfoService coinInfoService;

    public SelectedCoinScheduler(CoinInfoService coinInfoService) {
        this.coinInfoService = coinInfoService;
    }


    @Scheduled(fixedRate = 900000)  // 15분마다 실행
    public void saveSelectedCoinPriceInfo() {
        log.info(DateUtil.getCurrentTimestamp() + " schedule start");

        coinInfoService.saveSelectedCoinData().subscribe();
    }



}
