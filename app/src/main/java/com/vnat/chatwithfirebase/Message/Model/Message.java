package com.vnat.chatwithfirebase.Message.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String message;
    private String sender;
    private String time;

    public Message(String message, String sender) {
        this.message = message;
        this.sender = sender;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd/MM/yyyy HH:mm:ss");
        this.time = simpleDateFormat.format(new Date().getTime());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}