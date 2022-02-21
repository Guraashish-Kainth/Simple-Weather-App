 package com.example.simpleweather;

public class Weather {
    protected double curTemp;
    protected double minTemp;
    protected double maxTemp;
    protected double feelsLike;
    protected String description;
    protected String icon;

    public Weather() {
        super();
    }

    public double getCurTemp(){
        return curTemp;
    }

    public void setCurTemp(double curTemp) {
        this.curTemp = curTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String toString() {
        String result;
        result = curTemp + " " + minTemp+ " " + maxTemp + " " + feelsLike + " " + description;
        return result;
    }
}
