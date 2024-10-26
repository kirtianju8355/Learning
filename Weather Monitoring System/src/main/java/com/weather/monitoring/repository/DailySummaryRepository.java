package com.weather.monitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weather.monitoring.model.DailySummary;

import java.time.LocalDate;
import java.util.List;

public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {
    List<DailySummary> findByCityAndDate(String city, LocalDate date);
}


