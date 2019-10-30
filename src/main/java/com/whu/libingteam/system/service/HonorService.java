package com.whu.libingteam.system.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.system.dao.HonorDao;
import com.whu.libingteam.system.entity.Honor;
import com.whu.libingteam.system.entity.HonorDetailMapper;
import com.whu.libingteam.system.entity.HonorExample;
import com.whu.libingteam.system.entity.HonorPostMapper;
import com.whu.libingteam.system.entity.HonorSimpleMapper;
import com.whu.libingteam.system.entity.HonorUpdateMapper;
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
public class HonorService {
  @Autowired
  private HonorDao honorDao;

  /**
   * 分页计数函数 */
  private HonorExample initPageRowQueryFilter(Long page, Integer rows) {
    HonorExample example = new HonorExample();
    example.setOrderByClause("updateTime desc");
    HonorExample.Criteria criteria = example.createCriteria();
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
  private HonorExample initQueryFilter(Long page, Integer rows, Integer id, Date attainTime,
      String detail, Date createTime, String createBy, Date updateTime, String updateBy,
      Byte isDelete) {
    HonorExample example = new HonorExample();
    example.setOrderByClause("updateTime desc");
    HonorExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
    }
    if(attainTime!=null) {
      criteria.andAttainTimeEqualTo(attainTime);
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

  public Long getHonorCount() throws StatusException {
    return honorDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getHonorFilterCount(Integer id, Date attainTime, String detail, Date createTime,
      String createBy, Date updateTime, String updateBy, Byte isDelete) throws StatusException {
    return honorDao.countByExample(initQueryFilter(0L, 0, id, attainTime, detail, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public Honor getHonorByPrimaryKey(Integer key) throws StatusException {
    return honorDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getHonorSimpleMapByPrimaryKey(Integer key) throws StatusException {
    Honor entity = honorDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return HonorSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getHonorDetailMapByPrimaryKey(Integer key) throws StatusException {
    Honor entity = honorDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return HonorDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getHonorSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Honor entity: honorDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(HonorSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getHonorDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Honor entity: honorDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(HonorDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postHonor(HonorPostMapper postMapper, String createBy, String updateBy)
      throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    Honor entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(honorDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return HonorDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postHonorList(List<HonorPostMapper> postMappers, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (HonorPostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postHonor(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updateHonor(HonorUpdateMapper updateMapper, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Honor entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(honorDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return HonorDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updateHonorList(List<HonorUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (HonorUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updateHonor(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deleteHonor(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Honor entity = new Honor();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(honorDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deleteHonorList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deleteHonor(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverHonor(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Honor entity = new Honor();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(honorDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverHonorList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverHonor(key, updateBy));
    }
    return count.get();
  }
}
