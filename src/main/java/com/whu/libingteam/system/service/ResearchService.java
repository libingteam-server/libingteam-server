package com.whu.libingteam.system.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.system.dao.ResearchDao;
import com.whu.libingteam.system.entity.Research;
import com.whu.libingteam.system.entity.ResearchDetailMapper;
import com.whu.libingteam.system.entity.ResearchExample;
import com.whu.libingteam.system.entity.ResearchPostMapper;
import com.whu.libingteam.system.entity.ResearchSimpleMapper;
import com.whu.libingteam.system.entity.ResearchUpdateMapper;
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
public class ResearchService {
  @Autowired
  private ResearchDao researchDao;

  /**
   * 分页计数函数 */
  private ResearchExample initPageRowQueryFilter(Long page, Integer rows) {
    ResearchExample example = new ResearchExample();
    example.setOrderByClause("updateTime desc");
    ResearchExample.Criteria criteria = example.createCriteria();
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
  private ResearchExample initQueryFilter(Long page, Integer rows, Integer id, String publishTime,
      String detail, Date createTime, String createBy, Date updateTime, String updateBy,
      Byte isDelete) {
    ResearchExample example = new ResearchExample();
    example.setOrderByClause("updateTime desc");
    ResearchExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
    }
    if(publishTime!=null && !publishTime.equals("")) {
      criteria.andPublishTimeEqualTo(publishTime);
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

  public Long getResearchCount() throws StatusException {
    return researchDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getResearchFilterCount(Integer id, String publishTime, String detail, Date createTime,
      String createBy, Date updateTime, String updateBy, Byte isDelete) throws StatusException {
    return researchDao.countByExample(initQueryFilter(0L, 0, id, publishTime, detail, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public Research getResearchByPrimaryKey(Integer key) throws StatusException {
    return researchDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getResearchSimpleMapByPrimaryKey(Integer key) throws StatusException {
    Research entity = researchDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return ResearchSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getResearchDetailMapByPrimaryKey(Integer key) throws StatusException {
    Research entity = researchDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return ResearchDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getResearchSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Research entity: researchDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(ResearchSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getResearchDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Research entity: researchDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(ResearchDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postResearch(ResearchPostMapper postMapper, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    Research entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(researchDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return ResearchDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postResearchList(List<ResearchPostMapper> postMappers,
      String createBy, String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (ResearchPostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postResearch(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updateResearch(ResearchUpdateMapper updateMapper, String updateBy)
      throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Research entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(researchDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return ResearchDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updateResearchList(List<ResearchUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (ResearchUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updateResearch(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deleteResearch(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Research entity = new Research();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(researchDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deleteResearchList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deleteResearch(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverResearch(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Research entity = new Research();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(researchDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverResearchList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverResearch(key, updateBy));
    }
    return count.get();
  }
}
