package com.comba.android.combacommon.contacts;

/**
 * Created by chenhailin on 2017/6/1.
 */

public class Contact  {
    String phoneNUm;
    String  name;
    String  emial ;
    String  address;
    String photo;
    String nickName;
    String organisation;

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getRaw_id() {

        return raw_id;
    }

    public void setRaw_id(String raw_id) {
        this.raw_id = raw_id;
    }

    String raw_id;

    public String getPhoneNUm() {
        return phoneNUm;
    }

    public void setPhoneNUm(String phoneNUm) {
        this.phoneNUm = phoneNUm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
