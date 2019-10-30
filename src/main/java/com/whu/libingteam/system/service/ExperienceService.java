package com.whu.libingteam.system.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.system.dao.ExperienceDao;
import com.whu.libingteam.system.entity.Experience;
import com.whu.libingteam.system.entity.ExperienceDetailMapper;
import com.whu.libingteam.system.entity.ExperienceExample;
import com.whu.libingteam.system.entity.ExperiencePostMapper;
import com.whu.libingteam.system.entity.ExperienceSimpleMapper;
import com.whu.libingteam.system.entity.ExperienceUpdateMapper;
import java.lang.Byte;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Service
public class ExperienceService {
  @Autowired
  private ExperienceDao experienceDao;

  /**
   * 分页计数函数 */
  private ExperienceExample initPageRowQueryFilter(Long page, Integer rows) {
    ExperienceExample example = new ExperienceExample();
    example.setOrderByClause("updateTime desc");
    ExperienceExample.Criteria criteria = example.createCriteria();
    criteria.andIsDeleteEqualTo((byte) 0);
    if (page > 0) {
      Long offset = (page - 1L) * rows;
      example.setLimit(rows);
      example.setOffset(offset);
    }
    return example;
  }

  /**
   * 参数过滤函数 */
  private ExperienceExample initQueryFilter(Long page, Integer rows, Integer id,
      String experienceTime, String detail, Date createTime, String createBy, Date updateTime,
      String updateBy, Byte isDelete) {
    ExperienceExample example = new ExperienceExample();
    example.setOrderByClause("updateTime desc");
    ExperienceExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
    }
    if(experienceTime!=null && !experienceTime.equals("")) {
      criteria.andExperienceTimeEqualTo(experienceTime);
    }
    if(detail!=null && !detail.equals("")) {
      criteria.andDetailEqualTo(detail);
    }
    if(createTime!=null) {
      criteria.andCreateTimeEqualTo(createTime);
    }
    if(createBy!=null && !createBy.equals("")) {
      criteria.andCreateByEqualTo(createBy);
    }
    if(updateTime!=null) {
      criteria.andUpdateTimeEqualTo(updateTime);
    }
    if(updateBy!=null && !updateBy.equals("")) {
      criteria.andUpdateByEqualTo(updateBy);
    }
    if(isDelete!=null) {
      criteria.andIsDeleteEqualTo(isDelete);
    }
    if (page > 0) {
      Long offset = (page - 1L) * rows;
      example.setLimit(rows);
      example.setOffset(offset);
    }
    return example;
  }

  public Long getExperienceCount() throws StatusException {
    return experienceDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getExperienceFilterCount(Integer id, String experienceTime, String detail,
      Date createTime, String createBy, Date updateTime, String updateBy, Byte isDelete) throws
      StatusException {
    return experienceDao.countByExample(initQueryFilter(0L, 0, id, experienceTime, detail, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public Experience getExperienceByPrimaryKey(Integer key) throws StatusException {
    return experienceDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getExperienceSimpleMapByPrimaryKey(Integer key) throws
      StatusException {
    Experience entity = experienceDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return ExperienceSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getExperienceDetailMapByPrimaryKey(Integer key) throws
      StatusException {
    Experience entity = experienceDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return ExperienceDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getExperienceSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Experience entity: experienceDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(ExperienceSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getExperienceDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Experience entity: experienceDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(ExperienceDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postExperience(ExperiencePostMapper postMapper, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    Experience entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(experienceDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return ExperienceDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postExperienceList(List<ExperiencePostMapper> postMappers,
      String createBy, String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (ExperiencePostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postExperience(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updateExperience(ExperienceUpdateMapper updateMapper, String updateBy)
      throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Experience entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(experienceDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return ExperienceDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updateExperienceList(List<ExperienceUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (ExperienceUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updateExperience(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deleteExperience(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Experience entity = new Experience();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(experienceDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deleteExperienceList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deleteExperience(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverExperience(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Experience entity = new Experience();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(experienceDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverExperienceList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverExperience(key, updateBy));
    }
    return count.get();
  }
}
