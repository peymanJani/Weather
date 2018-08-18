package com.example.peyman.jani.weather.JavaClass;

/**
 * Created by PC_peyman on 4/21/2018.
 */

public class City {
    private String name;

    public String getName() {
        return name;
    }

    public City setName(String name) {
        this.name = name;
        return this;
    }

    public String getResponseString() {
        return responseString;
    }

    public City setResponseString(String responseString) {
        this.responseString = responseString;
        return this;
    }

    private String responseString;
}
