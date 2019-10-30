package com.whu.libingteam.system.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.system.entity.Honor;
import com.whu.libingteam.system.entity.HonorExample;
import org.springframework.stereotype.Repository;

/**
 * HonorDao继承基类
 */
@Repository
public interface HonorDao extends MyBatisBaseDao<Honor, Integer, HonorExample> {
}