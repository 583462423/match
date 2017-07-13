package com.sduwh.match.model.entity;

public class MatchType2 {
    private Integer id;

    private String name;

    private Integer matchTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getMatchTypeId() {
        return matchTypeId;
    }

    public void setMatchTypeId(Integer matchTypeId) {
        this.matchTypeId = matchTypeId;
    }

    @Override
    public String toString() {
        return "MatchType2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", matchTypeId=" + matchTypeId +
                '}';
    }
}