package com.bernal.gilberto.firebasesample;

/**
 * Created by Registered User on 1/23/2017.
 */

public class InsuranceData {
        public String company_name;
        public String type;  //   auto, home, life, etc.
        public String contact_name;
        public String contact_phone_number;
        public String policy_number ;
        public String issue_date;
        public String expiry_date;
        public String face_amount;
        public String monthly_premium;
        public String comments;

    public InsuranceData(){}

    public String getCompany_name() {
        return company_name;
    }
    public String gettype() {  return type;   }

    public String getContact_name() {
        return contact_name;
    }

    public String getContact_phone_number() {
        return contact_phone_number;
    }

    public String getPolicy_number() {
        return policy_number;
    }

    public String getExpiry_date() {
        return expiry_date;
    }
    public String getissue_date() {
        return issue_date;
    }

    public String getFace_amount() {
        return face_amount;
    }

    public String getMonthly_premium() {
        return monthly_premium;
    }

    public String getComments() {        return comments;
    }

    public InsuranceData(String company_name, String type, String contact_name, String contact_phone_number, String policy_number, String expiry_date, String issue_date, String face_amount, String monthly_premium, String comments) {
        this.company_name = company_name;
        this.type = type;
        this.contact_name = contact_name;
        this.contact_phone_number = contact_phone_number;
        this.policy_number = policy_number;
        this.expiry_date = expiry_date;
        this.issue_date = issue_date;
        this.face_amount = face_amount;
        this.monthly_premium = monthly_premium;
        this.comments = comments;
    }
}
