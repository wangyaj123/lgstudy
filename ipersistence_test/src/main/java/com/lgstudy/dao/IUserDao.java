package com.lgstudy.dao;

import com.lgstudy.pojo.User;

import java.util.List;

public interface IUserDao {
    List<User> selectList();

    User selectOne(User user);
}
