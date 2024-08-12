package dev.app.cocospring.service;

import dev.app.cocospring.dto.SelectedCoinPriceDto;
import dev.app.cocospring.entity.SelectedCoinPriceEntity;
import dev.app.cocospring.repository.SelectedCoinPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelectedCoinPriceService {

    private final SelectedCoinPriceRepository selectedCoinPriceRepository;

    /*
     * 누적된 코인 가격 데이터 호출
     * */
    public List<SelectedCoinPriceDto> getRecentCoinPriceData(Date lastTimeStamp){
//        List<SelectedCoinPriceEntity> entityList = selectedCoinPriceRepository.findByCoinNameAndTimeStampGreaterThan(coinName, lastTimeStamp);
        List<SelectedCoinPriceEntity> entityList = selectedCoinPriceRepository.findByTimeStampGreaterThan(lastTimeStamp);
        return entityList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /*
    * SelectedCoinPriceEntity 데이터 SelectedCoinPriceDto로 변환
    * */
    private SelectedCoinPriceDto convertToDto(SelectedCoinPriceEntity entity){
        return SelectedCoinPriceDto.builder()
                .id(entity.getId())
                .coinName(entity.getCoinName())
                .transaction_date(entity.getTransactionDate())
                .type(entity.getType())
                .units_traded(entity.getUnitsTraded())
                .price(entity.getPrice())
                .total(entity.getTotal())
                .timeStamp(entity.getTimeStamp())
                .build();

    }




    /*
     * 관심있는 코인 데이터의 가격 정보 저장
     * */
    @Transactional
    public void insertSelectedCoinPrice(SelectedCoinPriceDto dto){
        SelectedCoinPriceEntity selectedCoinPriceEntity = new SelectedCoinPriceEntity();

//        DateFormat dateFomatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        selectedCoinPriceEntity.selectedCoinPrice(
                dto.getId(),
                dto.getCoinName(),
                dto.getTransaction_date(),
                dto.getType(),
                dto.getUnits_traded(),
                dto.getPrice(),
                dto.getTotal(),
                dto.getTimeStamp()

        );
        selectedCoinPriceRepository.save(selectedCoinPriceEntity);

    }


}
