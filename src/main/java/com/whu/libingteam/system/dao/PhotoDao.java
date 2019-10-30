package com.whu.libingteam.system.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.system.entity.Photo;
import com.whu.libingteam.system.entity.PhotoExample;
import org.springframework.stereotype.Repository;

/**
 * PhotoDao继承基类
 */
@Repository
public interface PhotoDao extends MyBatisBaseDao<Photo, Integer, PhotoExample> {
}