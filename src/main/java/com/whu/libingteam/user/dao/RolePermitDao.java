package com.whu.libingteam.user.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.user.entity.RolePermit;
import com.whu.libingteam.user.entity.RolePermitExample;
import com.whu.libingteam.user.entity.RolePermitKey;
import org.springframework.stereotype.Repository;

/**
 * RolePermitDao继承基类
 */
@Repository
public interface RolePermitDao extends MyBatisBaseDao<RolePermit, RolePermitKey, RolePermitExample> {
}