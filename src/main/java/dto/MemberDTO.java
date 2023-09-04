package dto;

import java.sql.Time;
import java.sql.Timestamp;

public class MemberDTO {
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String email;
    private String zipcode;
    private String address1;
    private String address2;
    private Timestamp signup_date;

    public MemberDTO(String id, String pw, String name, String phone, String email, String zipcode, String address1, String address2, Timestamp signup_date) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.zipcode = zipcode;
        this.address1 = address1;
        this.address2 = address2;
        this.signup_date = signup_date;
    }

    public MemberDTO() {
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public Timestamp getSignup_date() {
        return signup_date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setSignup_date(Timestamp signup_date) {
        this.signup_date = signup_date;
    }
    public String getFormeDate(){
        long currentTime = System.currentTimeMillis();
        long signupTime = this.signup_date.getTime();
        long gapSec = (currentTime-signupTime)/1000;
        if(gapSec<60){
            return "1분 이내 가입";
        }else if(gapSec<1800){
            return "30분 이내 가입";
        }else if(gapSec<3600){
            return "1시간 이내 가입.";
        }else{
            return signup_date.toString();
        }
    }
}
