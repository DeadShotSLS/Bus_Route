package com.example.busroute;

public class Bus_Save {

    private String status_bus;
    private String bus_available;
    private String seat_available;
    private String bus_no;
    private String loaction;

    public Bus_Save() {
    }

    public Bus_Save(String status_bus, String bus_available, String seat_available, String bus_no, String loaction) {
        this.status_bus = status_bus;
        this.bus_available = bus_available;
        this.seat_available = seat_available;
        this.bus_no = bus_no;
        this.loaction = loaction;
    }

    public String getStatus_bus() {
        return status_bus;
    }

    public void setStatus_bus(String status) {
        this.status_bus = status_bus;
    }

    public String getBus_available() {
        return bus_available;
    }

    public void setBus_available(String bus_available) {
        this.bus_available = bus_available;
    }

    public String getSeat_available() {
        return seat_available;
    }

    public void setSeat_available(String seat_available) {
        this.seat_available = seat_available;
    }

    public String getBus_no() {
        return bus_no;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }

    public String getLoaction() {
        return loaction;
    }

    public void setLoaction(String loaction) {
        this.loaction = loaction;
    }
}
