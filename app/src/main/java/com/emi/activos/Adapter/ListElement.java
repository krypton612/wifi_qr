package com.emi.activos.Adapter;

public class ListElement {

    public String ssid;
    public String pasword;
    public String seguridad;


    public ListElement(String ssid, String pasword, String seguridad) {
        this.ssid = ssid;
        this.pasword = pasword;
        this.seguridad = seguridad;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public String getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(String seguridad) {
        this.seguridad = seguridad;
    }
}
