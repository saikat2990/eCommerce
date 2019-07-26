package com.example.ecommerce.Model;

public class Users {

    private  String UserName,password,phnNumber;

    public Users(){

    }


    public Users(String userName, String password, String phnNumber) {
        this.UserName = userName;
        this.password = password;
        this.phnNumber = phnNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhnNumber() {
        return phnNumber;
    }

    public void setPhnNumber(String phnNumber) {
        this.phnNumber = phnNumber;
    }

}
