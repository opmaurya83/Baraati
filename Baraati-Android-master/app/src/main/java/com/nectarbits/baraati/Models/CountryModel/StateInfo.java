package com.nectarbits.baraati.Models.CountryModel;

/**
 * Created by nectarbits-06 on 11/4/16.
 */
public class StateInfo {

    private int id=0;
    private String name="";
    private int country_id=0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }
}
