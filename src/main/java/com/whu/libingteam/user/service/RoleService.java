package com.whu.libingteam.user.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.user.dao.RoleDao;
import com.whu.libingteam.user.entity.Role;
import com.whu.libingteam.user.entity.RoleDetailMapper;
import com.whu.libingteam.user.entity.RoleExample;
import com.whu.libingteam.user.entity.RolePostMapper;
import com.whu.libingteam.user.entity.RoleSimpleMapper;
import com.whu.libingteam.user.entity.RoleUpdateMapper;
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
public class RoleService {
  @Autowired
  private RoleDao roleDao;

  /**
   * 分页计数函数 */
  private RoleExample initPageRowQueryFilter(Long page, Integer rows) {
    RoleExample example = new RoleExample();
    example.setOrderByClause("updateTime desc");
    RoleExample.Criteria criteria = example.createCriteria();
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
  private RoleExample initQueryFilter(Long page, Integer rows, Integer id, String name, String note,
      Date createTime, String createBy, Date updateTime, String updateBy, Byte isDelete) {
    RoleExample example = new RoleExample();
    example.setOrderByClause("updateTime desc");
    RoleExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
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

  public Long getRoleCount() throws StatusException {
    return roleDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getRoleFilterCount(Integer id, String name, String note, Date createTime,
      String createBy, Date updateTime, String updateBy, Byte isDelete) throws StatusException {
    return roleDao.countByExample(initQueryFilter(0L, 0, id, name, note, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public Role getRoleByPrimaryKey(Integer key) throws StatusException {
    return roleDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getRoleSimpleMapByPrimaryKey(Integer key) throws StatusException {
    Role entity = roleDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return RoleSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getRoleDetailMapByPrimaryKey(Integer key) throws StatusException {
    Role entity = roleDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return RoleDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getRoleSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Role entity: roleDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(RoleSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getRoleDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Role entity: roleDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(RoleDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postRole(RolePostMapper postMapper, String createBy, String updateBy)
      throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    Role entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(roleDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return RoleDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postRoleList(List<RolePostMapper> postMappers, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (RolePostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postRole(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updateRole(RoleUpdateMapper updateMapper, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Role entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(roleDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return RoleDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updateRoleList(List<RoleUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (RoleUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updateRole(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deleteRole(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Role entity = new Role();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(roleDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deleteRoleList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deleteRole(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverRole(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Role entity = new Role();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(roleDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverRoleList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverRole(key, updateBy));
    }
    return count.get();
  }
}
