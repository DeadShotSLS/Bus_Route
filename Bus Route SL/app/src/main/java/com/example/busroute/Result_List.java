package com.example.busroute;

public class Result_List {
    private String route_no;
    private String  bus_no;

    public Result_List(String route_no, String bus_no) {
        this.route_no = route_no;
        this.bus_no = bus_no;
    }

    public String getRoute_no() {
        return route_no;
    }

    public void setRoute_no(String route_no) {
        this.route_no = route_no;
    }

    public String getBus_no() {
        return bus_no;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }
}
