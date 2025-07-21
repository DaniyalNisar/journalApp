package com.daniyal.journalApp.service;


import com.daniyal.journalApp.api.WeatherResponse;
import com.daniyal.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private static final String WEATHER_URL = "http://api.weatherapi.com/v1/current.json?key=API_KEY&q=CITY";

    @Value("${weather_api_key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {

        try {
            WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
            if (weatherResponse != null) {
                return weatherResponse;
            } else {
                String URL = appCache.appCache.get(AppCache.keys.WEATHER_API.name()).replace("<city>", city).replace("<apiKey>", apiKey);
                ResponseEntity<WeatherResponse> response = restTemplate.exchange(URL, HttpMethod.GET, null, WeatherResponse.class);
                WeatherResponse body = response.getBody();
                if (body != null) {
                    redisService.set("weather_of_" + city, body, 300L);
                }
                return body;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
