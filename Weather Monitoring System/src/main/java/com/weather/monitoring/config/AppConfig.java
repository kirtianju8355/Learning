package com.weather.monitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@EnableScheduling
public class AppConfig {

    // Inject properties from application.properties
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.cities}")
    private List<String> cities;

    @Value("${weather.interval.minutes}")
    private int intervalMinutes;

    @Value("${threshold.temperature.alert}")
    private double temperatureThreshold;

    // Bean to provide RestTemplate for HTTP calls
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Getters for configuration properties, if needed in other components

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public List<String> getCities() {
        return cities;
    }

    public int getIntervalMinutes() {
        return intervalMinutes;
    }

    public double getTemperatureThreshold() {
        return temperatureThreshold;
    }
}

