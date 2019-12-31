package com.anguokeji.smartlock.vipclient.forms;

public class GrantLockToAppForm {
    private String grantCode;

    public GrantLockToAppForm(String grantCode) {
        this.grantCode = grantCode;
    }

    public GrantLockToAppForm() {
    }

    public String getGrantCode() {
        return grantCode;
    }

    public void setGrantCode(String grantCode) {
        this.grantCode = grantCode;
    }
}
