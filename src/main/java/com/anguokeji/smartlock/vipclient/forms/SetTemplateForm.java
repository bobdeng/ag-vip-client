package com.anguokeji.smartlock.vipclient.forms;


public class SetTemplateForm {
    private long id;
    private String data;

    public SetTemplateForm(long id, String data) {
        this.id = id;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public SetTemplateForm() {
    }
}
