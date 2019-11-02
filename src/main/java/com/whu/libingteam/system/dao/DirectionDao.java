package com.whu.libingteam.system.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.system.entity.Direction;
import com.whu.libingteam.system.entity.DirectionExample;
import org.springframework.stereotype.Repository;

/**
 * DirectionDao继承基类
 */
@Repository
public interface DirectionDao extends MyBatisBaseDao<Direction, Integer, DirectionExample> {
}