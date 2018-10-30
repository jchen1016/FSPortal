package com.decathlon.finance.taxreport.service;

import com.decathlon.finance.taxreport.model.User;

import java.util.List;

public interface UserService {
    int addUser(User user);

    List<User> findAllUser(int pageNum, int pageSize);
}
