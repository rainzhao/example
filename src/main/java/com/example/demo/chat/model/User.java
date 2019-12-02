package com.example.demo.chat.model;

/**
 * @author zhaoyu
 * @date 2019-05-18
 */
public class User {
    private String id;

    private String username;

    private String password;

    private String face_image;

    private String face_image_big;

    private String nickname;

    private String qrcode;

    private String cid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFace_image() {
        return face_image;
    }

    public void setFace_image(String face_image) {
        this.face_image = face_image;
    }

    public String getFace_image_big() {
        return face_image_big;
    }

    public void setFace_image_big(String face_image_big) {
        this.face_image_big = face_image_big;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
