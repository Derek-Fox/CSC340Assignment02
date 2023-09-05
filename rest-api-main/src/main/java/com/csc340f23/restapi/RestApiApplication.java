package com.csc340f23.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
        getWeather();
        System.exit(0);
    }

    /**
     * Gets the weather for the current day from api.open-meteo.com for Greensboro, NC.
     * Requests max and min temp for the day, as well as the peak chance for precipitation.
     */
    public static void getWeather() {
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=36.0726&longitude=-79.792&daily=temperature_2m_max,temperature_2m_min,precipitation_probability_max&temperature_unit=fahrenheit&windspeed_unit=mph&precipitation_unit=inch&timezone=America/New_York&forecast_days=1";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jsonWeather = restTemplate.getForObject(url, String.class);
            JsonNode tree = mapper.readTree(jsonWeather);

            //gets max temp
            double maxTemp = tree.at("/daily/temperature_2m_max/0").asDouble();
            //gets min temp
            double minTemp = tree.at("/daily/temperature_2m_min/0").asDouble();
            //gets max precipitation chance
            double rainChance = tree.at("/daily/precipitation_probability_max/0").asDouble();

            //print
            System.out.println("Max temperature: " + maxTemp);
            System.out.println("Min temperature: " + minTemp);
            System.out.println("Max Precipitation chance: " + rainChance + "%");

        } catch (JsonProcessingException ex) {
            System.out.println("error in getWeather");
        }
    }

}
