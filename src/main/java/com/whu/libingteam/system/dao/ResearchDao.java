package com.whu.libingteam.system.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.system.entity.Research;
import com.whu.libingteam.system.entity.ResearchExample;
import org.springframework.stereotype.Repository;

/**
 * ResearchDao继承基类
 */
@Repository
public interface ResearchDao extends MyBatisBaseDao<Research, Integer, ResearchExample> {
}