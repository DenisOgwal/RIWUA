package com.example.cropprotection.SMS;

public class SentMessage {

    public String Sender;
    public String Message;
    public String timesent;

    public SentMessage() {

    }

    public SentMessage(String Sender, String Message, String timesent ) {
        this.Sender = Sender;
        this.Message = Message;
        this.timesent = timesent;
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
}