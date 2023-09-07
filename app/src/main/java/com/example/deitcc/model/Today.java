package com.example.deitcc.model;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Today {
    private String calories;
    private String carbohydrates;
    private String protein;
    private String fat;
    private String date;

    public Today(String calories, String carbohydrates, String protein, String fat) {
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.fat = fat;
        this.date = String.valueOf(new java.sql.Date(System.currentTimeMillis()));
    }

    public Today(){
        this.date = String.valueOf(new java.sql.Date(System.currentTimeMillis()));
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }
    public String getDate() {
        return date;
    }
}

