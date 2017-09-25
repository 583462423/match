package com.sduwh.match.model.entity;

public class Grade {
    private Integer id;

    private Float score;

    private Integer level;

    private Integer matchItemId;

    private String comment;

    private int raterId;

    public int getRaterId() {
        return raterId;
    }

    public void setRaterId(int raterId) {
        this.raterId = raterId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMatchItemId() {
        return matchItemId;
    }

    public void setMatchItemId(Integer matchItemId) {
        this.matchItemId = matchItemId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}