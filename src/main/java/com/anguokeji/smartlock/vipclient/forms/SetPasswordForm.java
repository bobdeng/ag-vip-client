package com.anguokeji.smartlock.vipclient.forms;


public class SetPasswordForm {
    private long id;
    private String password;

    public SetPasswordForm(long id, String password) {
        this.id = id;
        this.password = password;
    }

    public SetPasswordForm() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
