package com.example.userrequests;

import java.util.Date;

public class Bid {
    public int id;
    public String bidText;
    public Date datePost;
    public int userId;
    public String status;
    public int adminId;
    public String roomName;

    public String Tema;
    //public Date DataClosed;
    public String SourceSoftware;
    public String SoftwareName;
    public String NetworkOrInventoryNumber;
    public String Workstation;
    public String FIOShort;


    public Bid(){
        Bid(0, "", new Date(), 0, "", 0, "", "", "", "", "", "", "");
    }


    public Bid(int id, String bidText, Date datePost, int userId, String status, int adminId, String roomName,
               String Tema, String SourceSoftware, String SoftwareName, String NetworkOrInventoryNumber,
               String Workstation, String FIOShort){
        Bid(id, bidText, datePost, userId, status, adminId, roomName, Tema, SourceSoftware, SoftwareName,
                NetworkOrInventoryNumber, Workstation, FIOShort);
    }


    public void Bid(int id, String bidText, Date datePost, int userId, String status, int adminId, String roomName,
                             String Tema, String SourceSoftware, String SoftwareName, String NetworkOrInventoryNumber,
                             String Workstation, String FIOShort){
        this.id = id;
        this.bidText = bidText;
        this.datePost = datePost;
        this.userId = userId;
        this.status = status;
        this.adminId = adminId;
        this.roomName = roomName;
        this.Tema = Tema;
        this.SourceSoftware = SourceSoftware;
        this.SoftwareName = SoftwareName;
        this.NetworkOrInventoryNumber = NetworkOrInventoryNumber;
        this.Workstation = Workstation;
        this.FIOShort = FIOShort;
    }
}
