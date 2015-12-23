package com.celloud.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月22日 下午1:32:22
 */
@Controller
public class LoginAction {
    Logger logger = LoggerFactory.getLogger(LoginAction.class);
    @RequestMapping("login")
    public String login(String id) {
        logger.info("========");
        return "login";
    }
    @RequestMapping("login2")
    @ResponseBody
    public User login(User user) {
        user = new User();
        user.setId(1);
        user.setPassword("123");
        user.setUsername("admin");
        return user;
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
