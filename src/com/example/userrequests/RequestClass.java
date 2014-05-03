package com.example.userrequests;

import java.util.Date;

public class RequestClass {
    public int id;
    public String bidText;
    public Date datePost;
    public int userId;
    public String status;
    public int adminId;
    public String roomName;


    public RequestClass(){
        RequestClass(0, "", new Date(), 0, "", 0, "");
    }


    public RequestClass(int id, String bidText, Date datePost, int userId, String status, int adminId, String roomName){
        RequestClass(id, bidText, datePost, userId, status, adminId, roomName);
    }


    public void RequestClass(int id, String bidText, Date datePost, int userId, String status, int adminId, String roomName){
        this.id = id;
        this.bidText = bidText;
        this.datePost = datePost;
        this.userId = userId;
        this.status = status;
        this.adminId = adminId;
        this.roomName = roomName;


    }
}
