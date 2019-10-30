package com.whu.libingteam.user.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.user.entity.Role;
import com.whu.libingteam.user.entity.RoleExample;
import org.springframework.stereotype.Repository;

/**
 * RoleDao继承基类
 */
@Repository
public interface RoleDao extends MyBatisBaseDao<Role, Integer, RoleExample> {
}