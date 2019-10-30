package com.whu.libingteam.system.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.system.entity.Experience;
import com.whu.libingteam.system.entity.ExperienceExample;
import org.springframework.stereotype.Repository;

/**
 * ExperienceDao继承基类
 */
@Repository
public interface ExperienceDao extends MyBatisBaseDao<Experience, Integer, ExperienceExample> {
}