package com.whu.libingteam.user.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.user.entity.UserRole;
import com.whu.libingteam.user.entity.UserRoleExample;
import com.whu.libingteam.user.entity.UserRoleKey;
import org.springframework.stereotype.Repository;

/**
 * UserRoleDao继承基类
 */
@Repository
public interface UserRoleDao extends MyBatisBaseDao<UserRole, UserRoleKey, UserRoleExample> {
}