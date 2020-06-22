package com.it.domain;

/**
 * @author 18576756475
 * @version V1.0
 * @ClassName User
 * @Description TODO
 * @Date 2019-12-05 12:56:00
 **/
public class User {

    private String id;

    private String username;

    private String headImage;

    private String sex;

    private String birthDay;

    private String education;

    private String interest;

    private String cTime;

    private String token;

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getHeadImage() {
        return headImage;
    }

    public User setHeadImage(String headImage) {
        this.headImage = headImage;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getEducation() {
        return education;
    }

    public User setEducation(String education) {
        this.education = education;
        return this;
    }

    public String getInterest() {
        return interest;
    }

    public User setInterest(String interest) {
        this.interest = interest;
        return this;
    }

    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    public String getcTime() {
        return cTime;
    }

    public User setcTime(String cTime) {
        this.cTime = cTime;
        return this;
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }
}
