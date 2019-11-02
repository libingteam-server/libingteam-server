package com.whu.libingteam.system.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.system.dao.DirectionDao;
import com.whu.libingteam.system.entity.Direction;
import com.whu.libingteam.system.entity.DirectionDetailMapper;
import com.whu.libingteam.system.entity.DirectionExample;
import com.whu.libingteam.system.entity.DirectionPostMapper;
import com.whu.libingteam.system.entity.DirectionSimpleMapper;
import com.whu.libingteam.system.entity.DirectionUpdateMapper;
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
public class DirectionService {
  @Autowired
  private DirectionDao directionDao;

  /**
   * 分页计数函数 */
  private DirectionExample initPageRowQueryFilter(Long page, Integer rows) {
    DirectionExample example = new DirectionExample();
    example.setOrderByClause("updateTime desc");
    DirectionExample.Criteria criteria = example.createCriteria();
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
  private DirectionExample initQueryFilter(Long page, Integer rows, Integer id, String introduction,
      String link, String topic, Date createTime, String createBy, Date updateTime, String updateBy,
      Byte isDelete) {
    DirectionExample example = new DirectionExample();
    example.setOrderByClause("updateTime desc");
    DirectionExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
    }
    if(introduction!=null && !introduction.equals("")) {
      criteria.andIntroductionEqualTo(introduction);
    }
    if(link!=null && !link.equals("")) {
      criteria.andLinkEqualTo(link);
    }
    if(topic!=null && !topic.equals("")) {
      criteria.andTopicEqualTo(topic);
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

  public Long getDirectionCount() throws StatusException {
    return directionDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getDirectionFilterCount(Integer id, String introduction, String link, String topic,
      Date createTime, String createBy, Date updateTime, String updateBy, Byte isDelete) throws
      StatusException {
    return directionDao.countByExample(initQueryFilter(0L, 0, id, introduction, link, topic, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public Direction getDirectionByPrimaryKey(Integer key) throws StatusException {
    return directionDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getDirectionSimpleMapByPrimaryKey(Integer key) throws StatusException {
    Direction entity = directionDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return DirectionSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getDirectionDetailMapByPrimaryKey(Integer key) throws StatusException {
    Direction entity = directionDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return DirectionDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getDirectionSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Direction entity: directionDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(DirectionSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getDirectionDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Direction entity: directionDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(DirectionDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postDirection(DirectionPostMapper postMapper, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    Direction entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(directionDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return DirectionDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postDirectionList(List<DirectionPostMapper> postMappers,
      String createBy, String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (DirectionPostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postDirection(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updateDirection(DirectionUpdateMapper updateMapper, String updateBy)
      throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Direction entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(directionDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return DirectionDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updateDirectionList(List<DirectionUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (DirectionUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updateDirection(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deleteDirection(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Direction entity = new Direction();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(directionDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deleteDirectionList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deleteDirection(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverDirection(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Direction entity = new Direction();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(directionDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverDirectionList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverDirection(key, updateBy));
    }
    return count.get();
  }
}
