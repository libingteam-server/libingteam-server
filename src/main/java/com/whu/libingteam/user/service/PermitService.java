package com.whu.libingteam.user.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.user.dao.PermitDao;
import com.whu.libingteam.user.entity.Permit;
import com.whu.libingteam.user.entity.PermitDetailMapper;
import com.whu.libingteam.user.entity.PermitExample;
import com.whu.libingteam.user.entity.PermitPostMapper;
import com.whu.libingteam.user.entity.PermitSimpleMapper;
import com.whu.libingteam.user.entity.PermitUpdateMapper;
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
public class PermitService {
  @Autowired
  private PermitDao permitDao;

  /**
   * 分页计数函数 */
  private PermitExample initPageRowQueryFilter(Long page, Integer rows) {
    PermitExample example = new PermitExample();
    example.setOrderByClause("updateTime desc");
    PermitExample.Criteria criteria = example.createCriteria();
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
  private PermitExample initQueryFilter(Long page, Integer rows, Integer id, String sysid,
      String group, String name, String note, Date createTime, String createBy, Date updateTime,
      String updateBy, Byte isDelete) {
    PermitExample example = new PermitExample();
    example.setOrderByClause("updateTime desc");
    PermitExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
    }
    if(sysid!=null && !sysid.equals("")) {
      criteria.andSysidEqualTo(sysid);
    }
    if(group!=null && !group.equals("")) {
      criteria.andGroupEqualTo(group);
    }
    if(name!=null && !name.equals("")) {
      criteria.andNameEqualTo(name);
    }
    if(note!=null && !note.equals("")) {
      criteria.andNoteEqualTo(note);
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

  public Long getPermitCount() throws StatusException {
    return permitDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getPermitFilterCount(Integer id, String sysid, String group, String name, String note,
      Date createTime, String createBy, Date updateTime, String updateBy, Byte isDelete) throws
      StatusException {
    return permitDao.countByExample(initQueryFilter(0L, 0, id, sysid, group, name, note, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public Permit getPermitByPrimaryKey(Integer key) throws StatusException {
    return permitDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getPermitSimpleMapByPrimaryKey(Integer key) throws StatusException {
    Permit entity = permitDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return PermitSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getPermitDetailMapByPrimaryKey(Integer key) throws StatusException {
    Permit entity = permitDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return PermitDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getPermitSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Permit entity: permitDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(PermitSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getPermitDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Permit entity: permitDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(PermitDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postPermit(PermitPostMapper postMapper, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    Permit entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(permitDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return PermitDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postPermitList(List<PermitPostMapper> postMappers,
      String createBy, String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (PermitPostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postPermit(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updatePermit(PermitUpdateMapper updateMapper, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Permit entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(permitDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return PermitDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updatePermitList(List<PermitUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (PermitUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updatePermit(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deletePermit(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Permit entity = new Permit();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(permitDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deletePermitList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deletePermit(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverPermit(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Permit entity = new Permit();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(permitDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverPermitList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverPermit(key, updateBy));
    }
    return count.get();
  }
}
