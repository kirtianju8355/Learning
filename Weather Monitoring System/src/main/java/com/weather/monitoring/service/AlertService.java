package com.weather.monitoring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.weather.monitoring.model.WeatherData;

@Service
public class AlertService {
    @Value("${threshold.temperature.alert}")
    private double threshold;

    public void checkForTemperatureAlerts(WeatherData weatherData) {
        if (weatherData.getTemperature() > threshold) {
            System.out.println("ALERT: Temperature exceeds threshold in " + weatherData.getCity() + 
                               " - Current Temperature: " + weatherData.getTemperature());
        }
    }
}

