package dev.app.cocospring.repository;

import dev.app.cocospring.entity.InterestCoinEntity;
import dev.app.cocospring.entity.SelectedCoinPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SelectedCoinPriceRepository extends JpaRepository<SelectedCoinPriceEntity, Long> {
//    List<SelectedCoinPriceEntity> findByCoinNameAndTimeStampGreaterThan(String coinName, Date timeStamp);
    // timestamp 이후의 데이터 모두 불러오기
    List<SelectedCoinPriceEntity> findByTimeStampGreaterThan(Date timeStamp);
}
