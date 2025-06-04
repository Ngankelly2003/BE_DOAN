package com.Graduation_Be.repository;

import com.Graduation_Be.model.AdvertisementEntity;
import com.Graduation_Be.model.RevenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<RevenueEntity, Long> {
    RevenueEntity findByAdvertisement(AdvertisementEntity advertisement);

    @Query("DELETE FROM RevenueEntity r WHERE r.advertisement.advertisementId = :advertiserId")
    @Modifying
    void deleteByAdvertisementAdvertiserId(@Param("advertiserId") Long advertiserId);
}