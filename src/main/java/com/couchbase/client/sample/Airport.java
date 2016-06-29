package com.couchbase.client.sample;

import com.couchbase.client.java.repository.annotation.Field;

/**
 * Created by ingenthr on 6/27/16.
 */
public class Airport {

    // airportname (string, indexed)
//    city (string, indexed)
//    country (string)
//    faa (string, indexed)
//    geo (object), child type:
//    alt (number)
//    lat (number)
//    lon (number)
//    icao (string, indexed)
//    id (number)
//    type (string, indexed)
//    tz (string)

    @Field("airportname")
    String name;

    @Field
    String faa;

    @Field
    String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaa() {
        return faa;
    }

    public void setFaa(String faa) {
        this.faa = faa;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "name='" + name + '\'' +
                ", faa='" + faa + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
