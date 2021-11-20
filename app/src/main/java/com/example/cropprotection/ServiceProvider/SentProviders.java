package com.example.cropprotection.ServiceProvider;

public class SentProviders {
    public int providerid;
    public String providername;
    public String contact;
    public String email;
    public String imageurl;
    public String timeregistered;
    public String services;
    public String description;

    public SentProviders() {

    }

    public SentProviders(int providerid, String providername, String contact, String email, String imageurl, String timeregistered, String services, String description ) {
        this.providerid = providerid;
        this.providername = providername;
        this.contact = contact;
        this.email = email;
        this.imageurl =imageurl;
        this.timeregistered = timeregistered;
        this.services = services;
        this.description = description;
    }

    public int getproviderid() {
        return providerid;
    }

    public void setproviderid(int providerid) {
        this.providerid = providerid;
    }

    public String getprovidername() {
        return providername;
    }

    public void setprovidername(String providername) {
        this.providername = providername;
    }

    public String getcontact() {
        return contact;
    }

    public void setcontact(String contact) {
        this.contact = contact;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getimageurl() {
        return imageurl;
    }

    public void setimageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public String gettimeregistered() {
        return timeregistered;
    }
    public void settimeregistered(String timeregistered) {
        this.timeregistered = timeregistered;
    }

    public String getservices() {
        return services;
    }

    public void setservices(String services) {
        this.services = services;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }
}
