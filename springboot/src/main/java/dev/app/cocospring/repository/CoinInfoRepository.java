package dev.app.cocospring.repository;

import dev.app.cocospring.entity.InterestCoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoinInfoRepository extends JpaRepository<InterestCoinEntity, Long> {
//    List<InterestCoinEntity> findAll();
    Optional<InterestCoinEntity> findByCoinName(String coin_name);

    List<InterestCoinEntity> findBySelectedTrue();
}
