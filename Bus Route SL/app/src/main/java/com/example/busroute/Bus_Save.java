package com.example.busroute;

public class Bus_Save {

    private String status_bus;
    private Boolean bus_available;
    private Boolean seat_available;
    private String bus_no_t;
    private String loaction;

    public Bus_Save() {
    }

    public Bus_Save(String status_bus, Boolean bus_available, Boolean seat_available, String bus_no_t, String loaction) {
        this.status_bus = status_bus;
        this.bus_available = bus_available;
        this.seat_available = seat_available;
        this.bus_no_t = bus_no_t;
        this.loaction = loaction;
    }

    public String getStatus_bus() {
        return status_bus;
    }

    public void setStatus_bus(String status) {
        this.status_bus = status_bus;
    }

    public Boolean getBus_available() {
        return bus_available;
    }

    public void setBus_available(Boolean bus_available) {
        this.bus_available = bus_available;
    }

    public Boolean getSeat_available() {
        return seat_available;
    }

    public void setSeat_available(Boolean seat_available) {
        this.seat_available = seat_available;
    }

    public String getBus_no_t(String bus_no_t) {
        return bus_no_t;
    }

    public void setBus_no_t(String bus_no_t) {
        this.bus_no_t = bus_no_t;
    }

    public String getLoaction() {
        return loaction;
    }

    public void setLoaction(String loaction) {
        this.loaction = loaction;
    }
}
