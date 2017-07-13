package com.sduwh.match.model.entity;

import java.util.Date;

public class User {
    private Integer id;

    private String username;

    private String password;

    private Integer status;

    private Integer roleId;

    private Role role;

    private String joinMatchIds;

    private Integer academyId;

    private Integer specialtyId;

    private Date lastTime;

    private String phone;

    private String email;

    private String name;  //人物姓名

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getJoinMatchIds() {
        return joinMatchIds;
    }

    public void setJoinMatchIds(String joinMatchIds) {
        this.joinMatchIds = joinMatchIds == null ? null : joinMatchIds.trim();
    }

    public Integer getAcademyId() {
        return academyId;
    }

    public void setAcademyId(Integer academyId) {
        this.academyId = academyId;
    }

    public Integer getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", role=" + role +
                ", joinMatchIds='" + joinMatchIds + '\'' +
                ", academyId=" + academyId +
                ", specialtyId=" + specialtyId +
                ", lastTime=" + lastTime +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}