package com.weather.monitoring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.monitoring.config.AppConfig;
import com.weather.monitoring.model.DailySummary;
import com.weather.monitoring.model.WeatherData;
import com.weather.monitoring.repository.DailySummaryRepository;
import com.weather.monitoring.repository.WeatherRepository;
import com.weather.monitoring.utils.TemperatureConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private DailySummaryRepository dailySummaryRepository;

    @Autowired
    private AlertService alertService;
    
    @Autowired
    private AppConfig appConfig;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.cities}")
    private List<String> cities;

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRateString = "${weather.interval.milliseconds}")
    public void fetchAndProcessWeatherData() {
        List<String> cities = appConfig.getCities();
        for (String city : cities) {
            String url = String.format("%s?q=%s&appid=%s", appConfig.getApiUrl(), city, appConfig.getApiKey());
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            processWeatherData(response, city);
        }
    }
    
    public List<WeatherData> getCurrentWeather() {
        return weatherRepository.findAll(); // Ensure this returns the current weather data
    }

    public List<DailySummary> getDailySummaries() {
        return dailySummaryRepository.findAll(); // Ensure this returns all daily summaries
    }

    private void processWeatherData(Map<String, Object> response, String city) {
        if (response == null) {
            System.out.println("No data received for city: " + city);
            return;
        }

        try {
            Map<String, Object> main = (Map<String, Object>) response.get("main");
            if (main == null) {
                System.out.println("No 'main' data available for city: " + city);
                return;
            }

            // Get the weather condition from the first element in the 'weather' array
            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
            String condition = "Unknown";
            if (weatherList != null && !weatherList.isEmpty()) {
                condition = (String) weatherList.get(0).get("main");
            }

            double temp = TemperatureConverter.kelvinToCelsius((Double) main.get("temp"));
            double feelsLike = TemperatureConverter.kelvinToCelsius((Double) main.get("feels_like"));
            LocalDateTime timestamp = LocalDateTime.now();

            WeatherData data = new WeatherData();
            data.setCity(city);
            data.setMainCondition(condition);
            data.setTemperature(temp);
            data.setFeelsLike(feelsLike);
            data.setTimestamp(timestamp);

            weatherRepository.save(data);

            alertService.checkForTemperatureAlerts(data);
            generateDailySummaries(city, timestamp.toLocalDate());

        } catch (ClassCastException e) {
            System.err.println("Error parsing weather data for city " + city + ": " + e.getMessage());
        }
    }

    public void generateDailySummaries(String city, LocalDate date) {
        List<WeatherData> data = weatherRepository.findByCityAndTimestampBetween(
                city,
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay()
        );

        double avgTemp = data.stream().mapToDouble(WeatherData::getTemperature).average().orElse(0);
        double maxTemp = data.stream().mapToDouble(WeatherData::getTemperature).max().orElse(0);
        double minTemp = data.stream().mapToDouble(WeatherData::getTemperature).min().orElse(0);
        String dominantCondition = findDominantCondition(data);

        DailySummary summary = new DailySummary();
        summary.setCity(city);
        summary.setDate(date);
        summary.setAvgTemp(avgTemp);
        summary.setMaxTemp(maxTemp);
        summary.setMinTemp(minTemp);
        summary.setDominantCondition(dominantCondition);

        dailySummaryRepository.save(summary);
    }

    private String findDominantCondition(List<WeatherData> data) {
        Map<String, Long> frequencyMap = new HashMap<>();
        for (WeatherData d : data) {
            frequencyMap.put(d.getMainCondition(), frequencyMap.getOrDefault(d.getMainCondition(), 0L) + 1);
        }
        return Collections.max(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}


