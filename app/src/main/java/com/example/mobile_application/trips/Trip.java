package com.example.mobile_application.trips;

public class Trip {
    private int id;
    private String name;
    private String destination;
    private String date;
    private String require;
    private String des;

    public Trip() {

    }

    public Trip(int id, String name, String destination, String date, String require, String des) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.date = date;
        this.require = require;
        this.des = des;
    }
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getDes() {
        return des;
    }

    public void setDesc(String des) {
        this.des = des;
    }
}
