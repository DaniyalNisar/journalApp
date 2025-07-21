package com.daniyal.journalApp.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherResponse {

    @JsonProperty("location")
    Location location;

    @JsonProperty("current")
    Current current;


    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Current getCurrent() {
        return current;
    }
    @Data
    @NoArgsConstructor
    public static class Location {

        @JsonProperty("name")
        String name;

        @JsonProperty("region")
        String region;

        @JsonProperty("country")
        String country;

        @JsonProperty("lat")
        double lat;

        @JsonProperty("lon")
        double lon;

        @JsonProperty("tz_id")
        String tzId;

        @JsonProperty("localtime_epoch")
        int localtimeEpoch;

        @JsonProperty("localtime")
        String localtime;


    }
    @Data
    @NoArgsConstructor
    public static class Condition {

        @JsonProperty("text")
        String text;

        @JsonProperty("icon")
        String icon;

        @JsonProperty("code")
        int code;

    }

    @Data
    @NoArgsConstructor
    public static class Current {

        @JsonProperty("temp_c")
        double tempC;

        @JsonProperty("temp_f")
        double tempF;

        @JsonProperty("is_day")
        int isDay;

        @JsonProperty("condition")
        Condition condition;

        @JsonProperty("wind_mph")
        double windMph;

        @JsonProperty("wind_kph")
        double windKph;

        @JsonProperty("wind_degree")
        int windDegree;

        @JsonProperty("wind_dir")
        String windDir;

        @JsonProperty("pressure_mb")
        int pressureMb;

        @JsonProperty("pressure_in")
        double pressureIn;

        @JsonProperty("precip_mm")
        int precipMm;

        @JsonProperty("precip_in")
        int precipIn;

        @JsonProperty("humidity")
        int humidity;

        @JsonProperty("cloud")
        int cloud;

        @JsonProperty("feelslike_c")
        double feelslikeC;

        @JsonProperty("feelslike_f")
        double feelslikeF;

        @JsonProperty("windchill_c")
        double windchillC;

        @JsonProperty("windchill_f")
        int windchillF;

        @JsonProperty("heatindex_c")
        int heatindexC;

        @JsonProperty("heatindex_f")
        double heatindexF;

        @JsonProperty("dewpoint_c")
        double dewpointC;

        @JsonProperty("dewpoint_f")
        double dewpointF;

        @JsonProperty("vis_km")
        int visKm;

        @JsonProperty("vis_miles")
        int visMiles;

        @JsonProperty("uv")
        double uv;

        @JsonProperty("gust_mph")
        double gustMph;

        @JsonProperty("gust_kph")
        double gustKph;

    }

}