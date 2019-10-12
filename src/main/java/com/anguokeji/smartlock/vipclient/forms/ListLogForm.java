package com.anguokeji.smartlock.vipclient.forms;


public class ListLogForm {
    private String lockId;
    private String date;

    public ListLogForm() {
    }

    public ListLogForm(String lockId, String date) {
        this.lockId = lockId;
        this.date = date;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
