package com.anguokeji.smartlock.vipclient.forms;


public class SetCardForm {
    private long id;
    private String cardId;

    public long getId() {
        return id;
    }

    public String getCardId() {
        return cardId;
    }

    public SetCardForm(long id, String cardId) {
        this.id = id;
        this.cardId = cardId;
    }

    public SetCardForm() {
    }
}
