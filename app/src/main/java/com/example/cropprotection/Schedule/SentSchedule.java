package com.example.cropprotection.Schedule;

public class SentSchedule {
    public int IDs;
    public String Venue;
    public String Subject;
    public String FromDate;
    public String ToDate;
    public String StartingTime;
    public String EndingTime;
    public String PostDate;
    public String Description;
    public SentSchedule() {

    }

    public SentSchedule(int IDs,String Venue, String Subject, String FromDate, String ToDate, String StartingTime , String EndingTime, String PostDate, String Description) {
        this.IDs = IDs;
        this.Venue = Venue;
        this.Subject= Subject;
        this.FromDate = FromDate;
        this.ToDate = ToDate;
        this.StartingTime = StartingTime;
        this.EndingTime = EndingTime;
        this.PostDate = PostDate;
        this.Description = Description;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getPostDate() {
        return PostDate;
    }
    public void setPostDate(String PostDate) {
        this.PostDate = PostDate;
    }

    public String getEndingTime() {
        return EndingTime;
    }
    public void setEndingTime(String EndingTime) {
        this.EndingTime = EndingTime;
    }

    public String getStartingTime() {
        return StartingTime;
    }
    public void setStartingTime(String StartingTime) {
        this.StartingTime = StartingTime;
    }

    public String getToDate() {
        return ToDate;
    }
    public void setToDate(String ToDate) {
        this.ToDate = ToDate;
    }

    public String getFromDate() {
        return FromDate;
    }
    public void setFromDate(String FromDate) {
        this.FromDate = FromDate;
    }

    public String getSubject() {
        return Subject;
    }
    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getVenue() {
        return Venue;
    }
    public void setVenue(String Venue) {
        this.Venue = Venue;
    }

    public int getid() {
        return IDs;
    }
    public void setid(int IDs) {
        this.IDs = IDs;
    }

}
