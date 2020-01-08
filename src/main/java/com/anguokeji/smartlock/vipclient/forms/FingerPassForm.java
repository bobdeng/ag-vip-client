package com.anguokeji.smartlock.vipclient.forms;

public class FingerPassForm {
    private String userMobile;
    private String lockId;
    private String name;

    public FingerPassForm(String userMobile, String lockId, String name) {
        this.userMobile = userMobile;
        this.lockId = lockId;
        this.name = name;
    }

    public FingerPassForm() {
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
