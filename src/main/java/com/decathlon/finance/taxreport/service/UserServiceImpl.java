package com.decathlon.finance.taxreport.service;

import com.github.pagehelper.PageHelper;
import com.decathlon.finance.taxreport.mapper.UserMapper;
import com.decathlon.finance.taxreport.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        //PageHelper.startPage(pageNum,pageSize);
        return userMapper.selectAllUser();
    }
}
