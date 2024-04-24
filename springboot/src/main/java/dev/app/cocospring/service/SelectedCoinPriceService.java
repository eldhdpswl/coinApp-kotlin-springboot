package dev.app.cocospring.service;

import dev.app.cocospring.dto.SelectedCoinPriceDto;
import dev.app.cocospring.entity.SelectedCoinPriceEntity;
import dev.app.cocospring.repository.SelectedCoinPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelectedCoinPriceService {

    private final SelectedCoinPriceRepository selectedCoinPriceRepository;

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
