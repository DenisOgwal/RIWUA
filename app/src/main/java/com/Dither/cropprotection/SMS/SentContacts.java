package com.Dither.cropprotection.SMS;

public class SentContacts {
    public String names;
    public String telephone;
    public String profilepic;

    public SentContacts() {

    }

    public SentContacts(String names, String telephone, String profilepic ) {
        this.names = names;
        this.telephone = telephone;
        this.profilepic = profilepic;
    }

    public String getnames() {
        return names;
    }

    public void setnames(String names) {
        this.names = names;
    }

    public String gettelephone() {
        return telephone;
    }

    public void settelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getprofilepic() {
        return profilepic;
    }

    public void setprofilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
