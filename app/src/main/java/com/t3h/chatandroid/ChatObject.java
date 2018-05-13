package com.t3h.chatandroid;

import java.util.Date;

/**
 * Created by Nga Gerrard on 12/05/2018.
 */

public class ChatObject {
    private String userName;
    private String message;
    private Date createdDate;

    public ChatObject(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public ChatObject(String userName, String message, Date createdDate) {
        this.userName = userName;
        this.message = message;
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public ChatObject() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
