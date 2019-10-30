package com.whu.libingteam.system.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.system.entity.News;
import com.whu.libingteam.system.entity.NewsExample;
import org.springframework.stereotype.Repository;

/**
 * NewsDao继承基类
 */
@Repository
public interface NewsDao extends MyBatisBaseDao<News, Integer, NewsExample> {
}