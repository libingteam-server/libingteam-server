package com.whu.libingteam.user.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.user.entity.User;
import com.whu.libingteam.user.entity.UserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserDao继承基类
 */
@Repository
public interface UserDao extends MyBatisBaseDao<User, Integer, UserExample> {
    User selectByAccount(@Param("account") String account);

    List<User> selectUsersByRoleId(@Param("roleId") Integer roleId);
}