package com.example.busroute;

public class Driver_Save {

    private String email_reg;
    private String pass;
    private String name_d;
    private String bus_no;
    private String phone;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Driver_Save(String email_reg, String pass, String name_d, String bus_no, String phone, String userid) {
        this.email_reg = email_reg;
        this.pass = pass;
        this.name_d = name_d;
        this.bus_no = bus_no;
        this.phone = phone;
        this.userid = userid;
    }

    public Driver_Save() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail_reg () {
            return email_reg;
        }

        public void setEmail_reg (String email_reg){
            this.email_reg = email_reg;
        }

        public String getPass () {
            return pass;
        }

        public void setPass (String pass){
            this.pass = pass;
        }

        public String getName_d () {
            return name_d;
        }

        public void setName_d (String name_d){
            this.name_d = name_d;
        }

        public String getBus_no () {
            return bus_no;
        }

        public void setBus_no (String bus_no){
            this.bus_no = bus_no;
        }


}
