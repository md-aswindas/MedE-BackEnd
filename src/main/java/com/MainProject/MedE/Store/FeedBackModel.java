package com.MainProject.MedE.Store;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@CrossOrigin
@Data
@Table(name = "feedBackTable")
public class FeedBackModel {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "FeedbackId")
    private Integer FeedbackId;

   @Column(name = "Store_id")
    private Integer Store_id;

   @Column(name = "user_id")
   private Integer user_id;

   @Column(name = "Comment")
    private String Comment;

   @Column(name = "Rating")
    private Integer Rating;

    public Integer getFeedbackId() {
        return FeedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        FeedbackId = feedbackId;
    }

    public Integer getStore_id() {
        return Store_id;
    }

    public void setStore_id(Integer store_id) {
        Store_id = store_id;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Integer getRating() {
        return Rating;
    }

    public void setRating(Integer rating) {
        Rating = rating;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
