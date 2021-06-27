package com.loco.photo.locophoto.bean

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id
    @Column(name="email")
    private String email
    @Column(name="name")
    private String name
    @Column(name="image_url")
    private String imageUrl
    @Column(name="banned")
    private boolean banned
    @Column(name="bio")
    private String bio

    User() { }


    User(String email, String name, String imageUrl, boolean banned, String bio) {
        this.setEmail(email)
        this.setName(name)
        this.setImageUrl(imageUrl)
        this.setBanned(banned)
        this.setBio(bio)
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getImageUrl() {
        return imageUrl
    }

    void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl
    }

    boolean getBanned() {
        return banned
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    boolean isBanned() {
        return banned
    }

    void setBanned(boolean banned) {
        this.banned = banned
    }

    String getBio() {
        return bio
    }

    void setBio(String about) {
        this.bio = about
    }
}
