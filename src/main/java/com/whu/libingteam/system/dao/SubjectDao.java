package com.whu.libingteam.system.dao;

import com.whu.libingteam.MyBatisBaseDao;
import com.whu.libingteam.system.entity.Subject;
import com.whu.libingteam.system.entity.SubjectExample;
import org.springframework.stereotype.Repository;

/**
 * SubjectDao继承基类
 */
@Repository
public interface SubjectDao extends MyBatisBaseDao<Subject, Integer, SubjectExample> {
}