package com.weather.monitoring.controller;

import com.weather.monitoring.model.DailySummary;
import com.weather.monitoring.model.WeatherData;
import com.weather.monitoring.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather-summary")
    public String weatherSummary(Model model) {
        // You might want to add daily summaries to the model if needed
        List<DailySummary> summaries = weatherService.getDailySummaries();
        model.addAttribute("summaries", summaries);  // Pass summaries to the view
        return "weather-summary";  // Returns the view name
    }

    @GetMapping("/api/weather")
    @ResponseBody
    public List<WeatherData> getCurrentWeather() {
        return weatherService.getCurrentWeather(); // Add this method in your WeatherService
    }

    @GetMapping("/api/daily-summaries")
    @ResponseBody
    public List<DailySummary> getDailySummaries() {
        return weatherService.getDailySummaries(); // Add this method in your WeatherService
    }
}


