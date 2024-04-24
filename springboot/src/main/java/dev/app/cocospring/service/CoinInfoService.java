package dev.app.cocospring.service;

import dev.app.cocospring.dto.InterestCoinDto;
import dev.app.cocospring.entity.InterestCoinEntity;
import dev.app.cocospring.repository.CoinInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoinInfoService {

    private final CoinInfoRepository coinInfoRepository;

    /*
    * 코인정보 저장
    * */
    @Transactional
    public void insertCoinInfo(InterestCoinDto dto){
        InterestCoinEntity interestCoinEntity = new InterestCoinEntity();
        interestCoinEntity.insertCoinInfo(
                dto.getId(),
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
                dto.getSelected()
        );
        coinInfoRepository.save(interestCoinEntity);

    }

    /*
     * 모든 코인정보 불러오기
     * */
    public List<InterestCoinDto> getAllInterestCoinData(){
        List<InterestCoinEntity> interestCoinEntityList = coinInfoRepository.findAll();

        return interestCoinEntityList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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
    * 사용자가 관심있어하는 코인만 가져오기
    * */
    public List<InterestCoinDto> getAllInterestSelectedCoinData(){
        List<InterestCoinEntity> selectedCoinEntityList = coinInfoRepository.findBySelectedTrue();

        return selectedCoinEntityList.stream()
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

}
