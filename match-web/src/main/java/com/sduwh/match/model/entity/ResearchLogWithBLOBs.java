package com.sduwh.match.model.entity;

public class ResearchLogWithBLOBs extends ResearchLog {
    private String content;

    private String comment;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}