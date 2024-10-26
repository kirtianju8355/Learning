package com.weather.monitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weather.monitoring.model.WeatherData;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
    List<WeatherData> findByCityAndTimestampBetween(String city, LocalDateTime start, LocalDateTime end);
}

