package com.example.busroute;

public class Route_Save {

    private String bus_no;
    private String route_no;
    private String path;

    public Route_Save() {
        this.bus_no = bus_no;
        this.route_no = route_no;
        this.path = path;
    }

    public Route_Save(String bus_no, String route, String path) {
        this.bus_no = bus_no;
        this.route_no = route;
        this.path = path;
    }

    public String getBus_no() {
        return bus_no;
    }

    public void setBus_no(String bus_no) {
        this.bus_no = bus_no;
    }

    public String getRoute_no() {
        return route_no;
    }

    public void setRoute_no(String route_no) {
        this.route_no = route_no;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
