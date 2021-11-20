package com.example.cropprotection.Notifications;

public class SentNotification {

    public String Sender;
    public String Message;
    public String timesent;
    public String Subject;

    public SentNotification() {

    }

    public SentNotification(String Sender, String Message, String timesent, String Subject ) {
        this.Sender = Sender;
        this.Message = Message;
        this.timesent = timesent;
        this.Subject = Subject;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String Sender) {
        this.Sender = Sender;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String gettimesent() {
        return timesent;
    }

    public void settimesent(String timesent) {
        this.timesent = timesent;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }
}