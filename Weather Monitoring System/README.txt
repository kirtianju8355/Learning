# Weather Monitoring Application

## Overview

This project is a real-time data processing system for weather monitoring. It retrieves weather data from the OpenWeatherMap API, processes it to generate daily summaries, and provides a simple HTML interface to display the data.

## Features

- Real-time weather data retrieval.
- Daily weather summaries including average, maximum, minimum temperatures, and dominant weather condition.
- User-configurable temperature alert thresholds.
- Simple UI to display weather summaries.

## Prerequisites

- Java 11 or higher
- Maven
- MySQL Database
- OpenWeatherMap API key (sign up at [OpenWeatherMap](https://openweathermap.org/) to get your free API key)

## Project Structure

src
└── main
    ├── java
    │   └── com
    │       └── weather
    │           └── monitoring
    │               ├── WeatherApplication.java
    │               ├── controller
    │               │   └── WeatherController.java
    │               ├── model
    │               │   ├── DailySummary.java
    │               │   └── WeatherData.java
    │               ├── repository
    │               │   ├── DailySummaryRepository.java
    │               │   └── WeatherRepository.java
    │               ├── service
    │               │   └── WeatherService.java
    │               └── utils
    │                   └── TemperatureConverter.java
    └── resources
        ├── application.properties
        └── templates
            └── weather-summary.html

Step 1: Setup MySQL Database

Create a MySQL Database: Log in to your MySQL server and create a new database:

CREATE DATABASE weather_db;

Step 2: Configure application.properties

Open the src/main/resources/application.properties file and configure the database connection and OpenWeatherMap API settings:

# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/weather_db?useSSL=false
spring.datasource.username=<your-database-username>
spring.datasource.password=<your-database-password>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# OpenWeatherMap API Configuration
weather.api.key=<your-openweathermap-api-key>
weather.api.url=https://api.openweathermap.org/data/2.5/weather
weather.cities=Delhi,Mumbai,Chennai,Bangalore,Kolkata,Hyderabad
weather.interval.milliseconds=300000  # 5 minutes in milliseconds

Step 3: the Project
Use Maven to build the project:

./mvnw clean install


Step 4: Run the Application
Start the Spring Boot application:

./mvnw spring-boot:run


Step 5: Access the Application
Open your web browser and navigate to:

Weather Summary Page: http://localhost:8080/weather-summary


Step 6: API Endpoints
You can also access the following API endpoints to retrieve weather data:

Current Weather Data: http://localhost:8080/api/weather
Daily Summaries: http://localhost:8080/api/daily-summaries


Step 7: Testing Alerts
Configure temperature alert thresholds in the code and monitor console output for alerts based on the fetched weather data.
Troubleshooting
Ensure your MySQL server is running and accessible.
Verify that your OpenWeatherMap API key is valid.
Check the application logs for any errors during startup or data retrieval.