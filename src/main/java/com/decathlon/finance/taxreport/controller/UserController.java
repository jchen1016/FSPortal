package com.decathlon.finance.taxreport.controller;

import com.decathlon.finance.taxreport.model.User;
import com.decathlon.finance.taxreport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user) {
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@RequestParam("id") int id) {
        return userService.findAllUser(1, 1);
    }

    @RequestMapping(value = "/hello")
    public String hello() {
        return "Hello";
    }


}
