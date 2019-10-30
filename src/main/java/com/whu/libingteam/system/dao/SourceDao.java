package com.whu.libingteam.system.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.system.entity.Source;
import com.whu.libingteam.system.entity.SourceExample;
import org.springframework.stereotype.Repository;

/**
 * SourceDao继承基类
 */
@Repository
public interface SourceDao extends MyBatisBaseDao<Source, Integer, SourceExample> {
}