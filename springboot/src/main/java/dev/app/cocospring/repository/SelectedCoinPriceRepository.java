package dev.app.cocospring.repository;

import dev.app.cocospring.entity.InterestCoinEntity;
import dev.app.cocospring.entity.SelectedCoinPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedCoinPriceRepository extends JpaRepository<SelectedCoinPriceEntity, Long> {

}
