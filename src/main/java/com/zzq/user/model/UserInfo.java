package com.zzq.user.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "user_info")
public class UserInfo implements Serializable {
    @Id
    private String uid;

    private String username;

    private String password;

    private String title;

    public UserInfo() {
    }

    public UserInfo(String username, String password, String title) {
        this.username = username;
        this.password = password;
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
