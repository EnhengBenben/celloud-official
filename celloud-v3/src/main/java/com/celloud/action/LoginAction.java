package com.celloud.action;

import org.springframework.ui.Model;

/**
 * 
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月22日 下午1:32:22
 */
public class LoginAction {
    public String login(String id) {
        return "login";
    }

    public User login(User user) {
        return new User();
    }
    public Model login(String username,String password,Model model){
        return model;
    }

}

class User {
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
