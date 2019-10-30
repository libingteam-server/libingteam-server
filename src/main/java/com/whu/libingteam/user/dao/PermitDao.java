package com.whu.libingteam.user.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.user.entity.Permit;
import com.whu.libingteam.user.entity.PermitExample;
import org.springframework.stereotype.Repository;

/**
 * PermitDao继承基类
 */
@Repository
public interface PermitDao extends MyBatisBaseDao<Permit, Integer, PermitExample> {
}