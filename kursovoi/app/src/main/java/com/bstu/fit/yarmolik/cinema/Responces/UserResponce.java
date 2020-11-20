package com.bstu.fit.yarmolik.cinema.Responces;

import java.io.Serializable;

public class UserResponce implements Serializable {
public String Id;
public Integer RoleId;
public String Email;
public String Login;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Integer getRoleId() {
        return RoleId;
    }

    public void setRoleId(Integer roleId) {
        RoleId = roleId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }
}
