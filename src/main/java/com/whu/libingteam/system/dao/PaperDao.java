package com.whu.libingteam.system.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.system.entity.Paper;
import com.whu.libingteam.system.entity.PaperExample;
import org.springframework.stereotype.Repository;

/**
 * PaperDao继承基类
 */
@Repository
public interface PaperDao extends MyBatisBaseDao<Paper, Integer, PaperExample> {
}