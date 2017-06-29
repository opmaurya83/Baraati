package com.nectarbits.baraati.Models.CountryModel;

/**
 * Created by nectarbits-06 on 11/4/16.
 */
public class CityInfo {

    private int id=0;
    private String cityName="";
    private int state_id=0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }
}
