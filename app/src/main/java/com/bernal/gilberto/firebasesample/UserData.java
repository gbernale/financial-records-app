package com.bernal.gilberto.firebasesample;

import java.util.List;

/**
 * Created by Gilberto Bernal on 1/10/2017.
 */

public class UserData {
    public   String username;
    public   String useraddress;
    public   String userPhone;
    List<InsuranceData> insuranceDataList;

    public UserData() {
    }


    public UserData(String username, String useraddress, String userPhone) {
        this.username = username;
        this.useraddress = useraddress;
        this.userPhone = userPhone;
    }

       public String getUsername() {
        return username;
    }

    public String getUseraddress(){
       return  useraddress;
    }

    public  String getUserPhone(){
        return userPhone;
    }

}


