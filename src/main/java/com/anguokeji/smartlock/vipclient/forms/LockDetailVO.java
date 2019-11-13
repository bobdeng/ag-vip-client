package com.anguokeji.smartlock.vipclient.forms;

public class LockDetailVO {
    private String id;
    private String mac;
    private String name;
    private NBDeviceVO nb;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public NBDeviceVO getNb() {
        return nb;
    }

    public void setNb(NBDeviceVO nb) {
        this.nb = nb;
    }
}
