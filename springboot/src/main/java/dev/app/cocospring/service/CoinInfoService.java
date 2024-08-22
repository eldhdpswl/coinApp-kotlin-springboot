package dev.app.cocospring.service;

import dev.app.cocospring.Util.DateUtil;
import dev.app.cocospring.dto.GetCoinInfo;
import dev.app.cocospring.dto.InterestCoinDto;
import dev.app.cocospring.entity.InterestCoinEntity;
import dev.app.cocospring.entity.SelectedCoinPriceEntity;
import dev.app.cocospring.repository.CoinInfoRepository;
import dev.app.cocospring.repository.SelectedCoinPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
//@RequiredArgsConstructor
@Transactional
public class CoinInfoService {

    private final CoinInfoRepository coinInfoRepository;

    private final SelectedCoinPriceRepository selectedCoinPriceRepository;

    // webclient
    private final WebClient webClient;

    public CoinInfoService(CoinInfoRepository coinInfoRepository, SelectedCoinPriceRepository selectedCoinPriceRepository, WebClient webClient) {
        this.coinInfoRepository = coinInfoRepository;
        this.selectedCoinPriceRepository = selectedCoinPriceRepository;
        this.webClient = webClient;
    }

//    @Autowired
//    public CoinInfoService(WebClient.Builder webClientBuilder, CoinInfoRepository coinInfoRepository) {
//        this.webClient = webClientBuilder.baseUrl("https://api.bithumb.com").build();
//        this.coinInfoRepository = coinInfoRepository;
//    }

    /*
     * post 전체데이터 호출하고 테이블에 저장(WebClient)
     * */
    public Mono<Void> saveCoinDate() {
        return webClient.get()
                .uri("/public/ticker/ALL")
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(response -> {
                    Map<String, Object> data = (Map<String, Object>) response.get("data");
                    data.forEach((key, value) -> {
                        if (!key.equals("date")) {
                            InterestCoinDto interestCoinDto = mapToDto(key, (Map<String, String>) value);
                            InterestCoinEntity interestCoinEntity = dtoToEntity(interestCoinDto);
                            coinInfoRepository.save(interestCoinEntity);
                        }
                    });
                    return Mono.empty();
                });
    }

    /*
     * Map을 InterestCoinDto로 변환
     * */
    private InterestCoinDto mapToDto(String coinName, Map<String, String> map){
        InterestCoinDto dto = new InterestCoinDto();
        dto.setCoin_name(coinName);
        dto.setOpening_price(map.get("opening_price"));
        dto.setClosing_price(map.get("closing_price"));
        dto.setMin_price(map.get("min_price"));
        dto.setMax_price(map.get("max_price"));
        dto.setUnits_traded(map.get("units_traded"));
        dto.setAcc_trade_value(map.get("acc_trade_value"));
        dto.setPrev_closing_price(map.get("prev_closing_price"));
        dto.setUnits_traded_24H(map.get("units_traded_24H"));
        dto.setAcc_trade_value_24H(map.get("acc_trade_value_24H"));
        dto.setFluctate_24H(map.get("fluctate_24H"));
        dto.setFluctate_rate_24H(map.get("fluctate_rate_24H"));
        return dto;
    }

    /*
     * InterestCoinDto를 InterestEntity로 변환
     * */
    private InterestCoinEntity dtoToEntity(InterestCoinDto dto) {
        InterestCoinEntity entity = new InterestCoinEntity();
        entity.insertCoinInfo(
                null, // ID는 null로 설정하여 자동 생성되도록 함
                dto.getCoin_name(),
                dto.getOpening_price(),
                dto.getClosing_price(),
                dto.getMin_price(),
                dto.getMax_price(),
                dto.getUnits_traded(),
                dto.getAcc_trade_value(),
                dto.getPrev_closing_price(),
                dto.getUnits_traded_24H(),
                dto.getAcc_trade_value_24H(),
                dto.getFluctate_24H(),
                dto.getFluctate_rate_24H(),
                false // 기본적으로 selected는 false로 설정
        );
        return entity;
    }


    /*
     * 코인 데이터 모두 불러오기
     * */
    public List<InterestCoinDto> getAllInterestCoinData(){
        List<InterestCoinEntity> interestCoinEntityList = coinInfoRepository.findAll();

        return interestCoinEntityList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /*
     * InterestEntity를 InterestCoinDto로 변환
     * */
    private InterestCoinDto convertToDto(InterestCoinEntity entity) {
        return InterestCoinDto.builder()
                .id(entity.getId())
                .coin_name(entity.getCoinName())
                .opening_price(entity.getOpeningPrice())
                .closing_price(entity.getClosingPrice())
                .min_price(entity.getMinPrice())
                .max_price(entity.getMaxPrice())
                .units_traded(entity.getUnitsTraded())
                .acc_trade_value(entity.getAccTradeValue())
                .prev_closing_price(entity.getPrevClosingPrice())
                .units_traded_24H(entity.getUnitsTraded24H())
                .acc_trade_value_24H(entity.getAccTradeValue24H())
                .fluctate_24H(entity.getFluctate24H())
                .fluctate_rate_24H(entity.getFluctateRate24H())
                .selected(entity.getSelected())
                .build();
    }


    /*
     * 코인 데이터 업데이트
     * */
    @Transactional
    public void updateInterestCoinData(InterestCoinDto dto) {
        Optional<InterestCoinEntity> targetEntity = coinInfoRepository.findByCoinName(dto.getCoin_name());
        InterestCoinEntity interestCoinEntity = targetEntity.get();

        // selected 값 변경
        interestCoinEntity.changeSelected(dto.getSelected());
    }



    /*
     * Selected 코인 데이터를 모두 불러와서 최신 거래 데이터 저장 (Scheduler에서 사용)
     * */
    public Mono<Void> saveSelectedCoinData(){
        List<InterestCoinDto> interestCoinDtoList = getAllInterestSelectedCoinData(); // selected True 데이터 호출
        if (interestCoinDtoList != null && !interestCoinDtoList.isEmpty()) {
            return Flux.fromIterable(interestCoinDtoList)
                    .flatMap(interestCoin -> selectedCoinData(interestCoin.getCoin_name()))
                    .then();  // 모든 작업이 완료된 후에 Mono<Void> 반환
        }
        return Mono.empty();
    }



    /*
     * selected 코인 최신 거래 데이터 저장 (Scheduler에서 사용)
     * */
    public Mono<Void> selectedCoinData(String coinName){
        String url = "public/transaction_history/" + coinName + "_KRW";
        log.info("coinName : " + coinName);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(response ->{
                    // 코인 거래 데이터 가져와서 List로 담기
                    List<Map<String, String>> dataList = (List<Map<String, String>>) response.get("data");
                    Map<String, String> recentData = dataList.get(0); // 최신 거래 데이터

                    SelectedCoinPriceEntity entity = new SelectedCoinPriceEntity();
                    try{
                        entity.selectedCoinPrice(
                                null,
                                coinName,
                                recentData.get("transaction_date"),
                                recentData.get("type"),
                                recentData.get("units_traded"),
                                recentData.get("price"),
                                recentData.get("total"),
                                DateUtil.convertStringToDate(DateUtil.getCurrentTimestamp())
                        );
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return Mono.error(e);
                    }
                    log.info("Saving entity: " + entity);
                    selectedCoinPriceRepository.save(entity);

                    return Mono.empty();

                });
    }



    /*
     * 사용자가 관심있어하는 코인만 가져오기
     * */
    public List<InterestCoinDto> getAllInterestSelectedCoinData(){
        List<InterestCoinEntity> selectedCoinEntityList = coinInfoRepository.findBySelectedTrue();

        return selectedCoinEntityList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }



    /*
     * 전체데이터 호출(WebClient 사용 & 테스트용)
     * */
    public Mono<GetCoinInfo> getCoindData() {
        return webClient.get()
                .uri("/public/ticker/ALL")
                .retrieve()
                .bodyToMono(GetCoinInfo.class);

    }



    /*
     * 코인정보 저장
     * */
//    @Transactional
//    public void insertCoinInfo(InterestCoinDto dto){
//        InterestCoinEntity interestCoinEntity = new InterestCoinEntity();
//        interestCoinEntity.insertCoinInfo(
//                dto.getId(),
//                dto.getCoin_name(),
//                dto.getOpening_price(),
//                dto.getClosing_price(),
//                dto.getMin_price(),
//                dto.getMax_price(),
//                dto.getUnits_traded(),
//                dto.getAcc_trade_value(),
//                dto.getPrev_closing_price(),
//                dto.getUnits_traded_24H(),
//                dto.getAcc_trade_value_24H(),
//                dto.getFluctate_24H(),
//                dto.getFluctate_rate_24H(),
//                dto.getSelected()
//        );
//        coinInfoRepository.save(interestCoinEntity);
//
//    }


}
