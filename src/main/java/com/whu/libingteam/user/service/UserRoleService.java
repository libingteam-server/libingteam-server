package com.whu.libingteam.user.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.user.dao.UserRoleDao;
import com.whu.libingteam.user.entity.UserRole;
import com.whu.libingteam.user.entity.UserRoleDetailMapper;
import com.whu.libingteam.user.entity.UserRoleExample;
import com.whu.libingteam.user.entity.UserRoleKey;
import com.whu.libingteam.user.entity.UserRolePostMapper;
import com.whu.libingteam.user.entity.UserRoleSimpleMapper;
import com.whu.libingteam.user.entity.UserRoleUpdateMapper;
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
public class UserRoleService {
  @Autowired
  private UserRoleDao userRoleDao;

  /**
   * 分页计数函数 */
  private UserRoleExample initPageRowQueryFilter(Long page, Integer rows) {
    UserRoleExample example = new UserRoleExample();
    example.setOrderByClause("updateTime desc");
    UserRoleExample.Criteria criteria = example.createCriteria();
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
  private UserRoleExample initQueryFilter(Long page, Integer rows, Date createTime, String createBy,
      Date updateTime, String updateBy, Byte isDelete, Integer userid, Integer roleid) {
    UserRoleExample example = new UserRoleExample();
    example.setOrderByClause("updateTime desc");
    UserRoleExample.Criteria criteria = example.createCriteria();
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
    if(userid!=null) {
      criteria.andUseridEqualTo(userid);
    }
    if(roleid!=null) {
      criteria.andRoleidEqualTo(roleid);
    }
    if (page > 0) {
      Long offset = (page - 1L) * rows;
      example.setLimit(rows);
      example.setOffset(offset);
    }
    return example;
  }

  public Long getUserRoleCount() throws StatusException {
    return userRoleDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getUserRoleFilterCount(Date createTime, String createBy, Date updateTime,
      String updateBy, Byte isDelete, Integer userid, Integer roleid) throws StatusException {
    return userRoleDao.countByExample(initQueryFilter(0L, 0, createTime, createBy, updateTime, updateBy, isDelete, userid, roleid));
  }

  public UserRole getUserRoleByPrimaryKey(UserRoleKey key) throws StatusException {
    return userRoleDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getUserRoleSimpleMapByPrimaryKey(UserRoleKey key) throws
      StatusException {
    UserRole entity = userRoleDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return UserRoleSimpleMapper.getMapWithExtra(entity,entity.getUserid(), entity.getRoleid());
  }

  public Map<String, Object> getUserRoleDetailMapByPrimaryKey(UserRoleKey key) throws
      StatusException {
    UserRole entity = userRoleDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return UserRoleDetailMapper.getMapWithExtra(entity,entity.getUserid(), entity.getRoleid());
  }

  public List<Map<String, Object>> getUserRoleSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (UserRole entity: userRoleDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(UserRoleSimpleMapper.getMapWithExtra(entity,entity.getUserid(), entity.getRoleid()));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getUserRoleDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (UserRole entity: userRoleDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(UserRoleDetailMapper.getMapWithExtra(entity,entity.getUserid(), entity.getRoleid()));
    }
    return entityMapList;
  }

  public Map<String, Object> postUserRole(UserRolePostMapper postMapper, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    UserRole entity = postMapper.getEntity();
    // 记录主键;
    entity.setUserid(postMapper.userid);
    entity.setRoleid(postMapper.roleid);
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(userRoleDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return UserRoleDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postUserRoleList(List<UserRolePostMapper> postMappers,
      String createBy, String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (UserRolePostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postUserRole(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updateUserRole(UserRoleUpdateMapper updateMapper, String updateBy)
      throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    UserRole entity = updateMapper.getEntity();
    // 记录主键;
    entity.setUserid(updateMapper.userid);
    entity.setRoleid(updateMapper.roleid);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(userRoleDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return UserRoleDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updateUserRoleList(List<UserRoleUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (UserRoleUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updateUserRole(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deleteUserRole(UserRoleKey key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    UserRole entity = new UserRole(key);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(userRoleDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deleteUserRoleList(List<UserRoleKey> keys, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (UserRoleKey key: keys) {
      // 更新计数;
      count.addAndGet(deleteUserRole(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverUserRole(UserRoleKey key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    UserRole entity = new UserRole(key);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(userRoleDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverUserRoleList(List<UserRoleKey> keys, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (UserRoleKey key: keys) {
      // 更新计数;
      count.addAndGet(recoverUserRole(key, updateBy));
    }
    return count.get();
  }
}
