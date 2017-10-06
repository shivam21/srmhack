package com.cropcart.fragments;

/**
 * Created by BHUSRI on 10/6/2017.
 */
public class CropChatVar {
    private String userid;
    private String time;
    private String status;
    private int msgid;
    private String chatid;

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;
}
