package com.sduwh.match.model.wrapper;

import com.sduwh.match.model.entity.Academy;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.Specialty;
import com.sduwh.match.model.entity.User;

import java.util.List;

/**
 * Created by qxg on 17-7-21.
 */
public class UserWrapper {
    private User user;                  //用户信息
    private String status;              //用户状态
    private List<MatchItem> joinMatch;  ///参加的所有比赛
    private Academy academy;            //学院信息
    private Specialty specialty;        //专业信息

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MatchItem> getJoinMatch() {
        return joinMatch;
    }

    public void setJoinMatch(List<MatchItem> joinMatch) {
        this.joinMatch = joinMatch;
    }

    public Academy getAcademy() {
        return academy;
    }

    public void setAcademy(Academy academy) {
        this.academy = academy;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "UserWrapper{" +
                "user=" + user +
                ", status='" + status + '\'' +
                ", joinMatch=" + joinMatch +
                ", academy=" + academy +
                ", specialty=" + specialty +
                '}';
    }
}
