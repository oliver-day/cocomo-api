package com.cocomo.dao;

import com.cocomo.pojo.User;

public interface UserMapper {

    User selectByPrimaryKey(Integer id);

    int insert(User project);
}
