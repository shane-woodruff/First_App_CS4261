package com.example.finalapp.model;

import java.util.Date;
import java.util.List;

public class Post {
    private String poster;
    private String postid;
    private String title;
    private String type;
    private String category;
    private int amount;
    private double price;
    private String detail;
    private Date postDate;
    private List<String> images;

    public Post(String poster, String postid, String title, String type, String category,
                int amount, double price, String detail, Date postDate, List<String> images) {
        this.poster = poster;
        this.postid = postid;
        this.title = title;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.price = price;
        this.detail = detail;
        this.postDate = postDate;
        this.images = images;
    }

    public Post() {

    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
