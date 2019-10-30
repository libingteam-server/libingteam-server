package com.whu.libingteam.user.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.user.dao.RolePermitDao;
import com.whu.libingteam.user.entity.RolePermit;
import com.whu.libingteam.user.entity.RolePermitDetailMapper;
import com.whu.libingteam.user.entity.RolePermitExample;
import com.whu.libingteam.user.entity.RolePermitKey;
import com.whu.libingteam.user.entity.RolePermitPostMapper;
import com.whu.libingteam.user.entity.RolePermitSimpleMapper;
import com.whu.libingteam.user.entity.RolePermitUpdateMapper;
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
public class RolePermitService {
  @Autowired
  private RolePermitDao rolePermitDao;

  /**
   * 分页计数函数 */
  private RolePermitExample initPageRowQueryFilter(Long page, Integer rows) {
    RolePermitExample example = new RolePermitExample();
    example.setOrderByClause("updateTime desc");
    RolePermitExample.Criteria criteria = example.createCriteria();
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
  private RolePermitExample initQueryFilter(Long page, Integer rows, String operation,
      Date createTime, String createBy, Date updateTime, String updateBy, Byte isDelete,
      Integer roleid, Integer permitid) {
    RolePermitExample example = new RolePermitExample();
    example.setOrderByClause("updateTime desc");
    RolePermitExample.Criteria criteria = example.createCriteria();
    if(operation!=null && !operation.equals("")) {
      criteria.andOperationEqualTo(operation);
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
    if(roleid!=null) {
      criteria.andRoleidEqualTo(roleid);
    }
    if(permitid!=null) {
      criteria.andPermitidEqualTo(permitid);
    }
    if (page > 0) {
      Long offset = (page - 1L) * rows;
      example.setLimit(rows);
      example.setOffset(offset);
    }
    return example;
  }

  public Long getRolePermitCount() throws StatusException {
    return rolePermitDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getRolePermitFilterCount(String operation, Date createTime, String createBy,
      Date updateTime, String updateBy, Byte isDelete, Integer roleid, Integer permitid) throws
      StatusException {
    return rolePermitDao.countByExample(initQueryFilter(0L, 0, operation, createTime, createBy, updateTime, updateBy, isDelete, roleid, permitid));
  }

  public RolePermit getRolePermitByPrimaryKey(RolePermitKey key) throws StatusException {
    return rolePermitDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getRolePermitSimpleMapByPrimaryKey(RolePermitKey key) throws
      StatusException {
    RolePermit entity = rolePermitDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return RolePermitSimpleMapper.getMapWithExtra(entity,entity.getRoleid(), entity.getPermitid());
  }

  public Map<String, Object> getRolePermitDetailMapByPrimaryKey(RolePermitKey key) throws
      StatusException {
    RolePermit entity = rolePermitDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return RolePermitDetailMapper.getMapWithExtra(entity,entity.getRoleid(), entity.getPermitid());
  }

  public List<Map<String, Object>> getRolePermitSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (RolePermit entity: rolePermitDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(RolePermitSimpleMapper.getMapWithExtra(entity,entity.getRoleid(), entity.getPermitid()));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getRolePermitDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (RolePermit entity: rolePermitDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(RolePermitDetailMapper.getMapWithExtra(entity,entity.getRoleid(), entity.getPermitid()));
    }
    return entityMapList;
  }

  public Map<String, Object> postRolePermit(RolePermitPostMapper postMapper, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    RolePermit entity = postMapper.getEntity();
    // 记录主键;
    entity.setRoleid(postMapper.roleid);
    entity.setPermitid(postMapper.permitid);
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(rolePermitDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return RolePermitDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postRolePermitList(List<RolePermitPostMapper> postMappers,
      String createBy, String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (RolePermitPostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postRolePermit(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updateRolePermit(RolePermitUpdateMapper updateMapper, String updateBy)
      throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    RolePermit entity = updateMapper.getEntity();
    // 记录主键;
    entity.setRoleid(updateMapper.roleid);
    entity.setPermitid(updateMapper.permitid);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(rolePermitDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return RolePermitDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updateRolePermitList(List<RolePermitUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (RolePermitUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updateRolePermit(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deleteRolePermit(RolePermitKey key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    RolePermit entity = new RolePermit(key);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(rolePermitDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deleteRolePermitList(List<RolePermitKey> keys, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (RolePermitKey key: keys) {
      // 更新计数;
      count.addAndGet(deleteRolePermit(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverRolePermit(RolePermitKey key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    RolePermit entity = new RolePermit(key);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(rolePermitDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverRolePermitList(List<RolePermitKey> keys, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (RolePermitKey key: keys) {
      // 更新计数;
      count.addAndGet(recoverRolePermit(key, updateBy));
    }
    return count.get();
  }
}
