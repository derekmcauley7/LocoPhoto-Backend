package com.loco.photo.locophoto.bean

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id
    @Column(name="lat")
    private Double lat
    @Column(name="lng")
    private Double lng
    @Column(name="city")
    private String city
    @Column(name="userID")
    private String userID
    @Column(name="date")
    private String date
    @Column(name="views")
    private Integer views
    @Column(name="likes")
    private Integer likes
    @Column(name="url")
    private String url
    @Column(name="comment")
    private String comment
    @Column(name="deleted")
    private Boolean deleted

    long getId() {
        return id
    }

    void setId(long id) {
        this.id = id
    }

    Double getLat() {
        return lat
    }

    void setLat(Double lat) {
        this.lat = lat
    }

    Double getLng() {
        return lng
    }

    void setLng(Double lng) {
        this.lng = lng
    }

    String getCity() {
        return city
    }

    void setCity(String city) {
        this.city = city
    }

    String getUserID() {
        return userID
    }

    void setUserID(String userID) {
        this.userID = userID
    }

    String getDate() {
        return date
    }

    void setDate(String date) {
        this.date = date
    }

    Integer getViews() {
        return views
    }

    void setViews(Integer views) {
        this.views = views
    }

    Integer getLikes() {
        return likes
    }

    void setLikes(Integer likes) {
        this.likes = likes
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    String getComment() {
        return comment
    }

    void setComment(String comment) {
        this.comment = comment
    }

    Boolean getdeleted() {
        return deleted
    }

    void setDeleted(Boolean deleted) {
        this.deleted = deleted
    }


    Image() {
    }

    Image(Double lat, Double lng, String city, String userID, String date, String url, String comment) {
        this.url = url
        this.lat = lat
        this.lng = lng
        this.city = city
        this.userID = userID
        this.date = date
        this.comment = comment
        likes = 0
        views = 0
    }
}
