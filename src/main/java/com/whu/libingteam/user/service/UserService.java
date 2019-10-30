package com.whu.libingteam.user.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.user.dao.UserDao;
import com.whu.libingteam.user.entity.User;
import com.whu.libingteam.user.entity.UserDetailMapper;
import com.whu.libingteam.user.entity.UserExample;
import com.whu.libingteam.user.entity.UserPostMapper;
import com.whu.libingteam.user.entity.UserSimpleMapper;
import com.whu.libingteam.user.entity.UserUpdateMapper;
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


import com.whu.libingteam.util.PwdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Service
public class UserService {
  @Autowired
  private UserDao userDao;

  /**
   * 分页计数函数 */
  private UserExample initPageRowQueryFilter(Long page, Integer rows) {
    UserExample example = new UserExample();
    example.setOrderByClause("updateTime desc");
    UserExample.Criteria criteria = example.createCriteria();
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
  private UserExample initQueryFilter(Long page, Integer rows, Integer id, String account,
      String password, String name, String email, String photo, String phone, String position,
      Date grade, String state, String introducation, String address, String researchDirection,
      String salt, Date createTime, String createBy, Date updateTime, String updateBy,
      Byte isDelete) {
    UserExample example = new UserExample();
    example.setOrderByClause("updateTime desc");
    UserExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
    }
    if(account!=null && !account.equals("")) {
      criteria.andAccountEqualTo(account);
    }
    if(password!=null && !password.equals("")) {
      criteria.andPasswordEqualTo(password);
    }
    if(name!=null && !name.equals("")) {
      criteria.andNameEqualTo(name);
    }
    if(email!=null && !email.equals("")) {
      criteria.andEmailEqualTo(email);
    }
    if(photo!=null && !photo.equals("")) {
      criteria.andPhotoEqualTo(photo);
    }
    if(phone!=null && !phone.equals("")) {
      criteria.andPhoneEqualTo(phone);
    }
    if(position!=null && !position.equals("")) {
      criteria.andPositionEqualTo(position);
    }
    if(grade!=null) {
      criteria.andGradeEqualTo(grade);
    }
    if(state!=null && !state.equals("")) {
      criteria.andStateEqualTo(state);
    }
    if(introducation!=null && !introducation.equals("")) {
      criteria.andIntroducationEqualTo(introducation);
    }
    if(address!=null && !address.equals("")) {
      criteria.andAddressEqualTo(address);
    }
    if(researchDirection!=null && !researchDirection.equals("")) {
      criteria.andResearchDirectionEqualTo(researchDirection);
    }
    if(salt!=null && !salt.equals("")) {
      criteria.andSaltEqualTo(salt);
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

  public Long getUserCount() throws StatusException {
    return userDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getUserFilterCount(Integer id, String account, String password, String name,
      String email, String photo, String phone, String position, Date grade, String state,
      String introducation, String address, String researchDirection, String salt, Date createTime,
      String createBy, Date updateTime, String updateBy, Byte isDelete) throws StatusException {
    return userDao.countByExample(initQueryFilter(0L, 0, id, account, password, name, email, photo, phone, position, grade, state, introducation, address, researchDirection, salt, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public User getUserByPrimaryKey(Integer key) throws StatusException {
    return userDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getUserSimpleMapByPrimaryKey(Integer key) throws StatusException {
    User entity = userDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return UserSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getUserDetailMapByPrimaryKey(Integer key) throws StatusException {
    User entity = userDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return UserDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getUserSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (User entity: userDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(UserSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getUserDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (User entity: userDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(UserDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postUser(UserPostMapper postMapper, String createBy, String updateBy)
      throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    User entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(userDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return UserDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postUserList(List<UserPostMapper> postMappers, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (UserPostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postUser(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updateUser(UserUpdateMapper updateMapper, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    User entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(userDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return UserDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updateUserList(List<UserUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (UserUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updateUser(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deleteUser(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    User entity = new User();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(userDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deleteUserList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deleteUser(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverUser(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    User entity = new User();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(userDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverUserList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverUser(key, updateBy));
    }
    return count.get();
  }

  /**
   * ==================================
   * 非CRUD业务逻辑代码
   * ==================================
   */
//
//  public Map<String, Object> login(String account, String password) throws StatusException {
//    User user = userDao.selectByAccount(account);
//    if (user == null) throw new StatusException("USER_NULL");
//    if (!PwdUtil.checkPassword(user.getPassword(), user.getSalt(), password))
//      throw new StatusException("PASSWORD_ERROR");
//    //将User和Token存放到Redis当中，并且设置过期时间
//    JedisUtil.set(account,PwdUtil.token());
//    userDao.updateByPrimaryKeySelective(user);
//    return getUserDetailMapByPrimaryKey(user.getId());
//  }
//
//  public int logout(int userId) throws StatusException {
//    User user = getUserByPrimaryKey(userId);
//    if (user == null) throw new StatusException("USER_NULL");
//    //将Token从Redis当中删除
//    JedisUtil.del(String.valueOf(userId));
//    return userDao.updateByPrimaryKeySelective(user);
//  }
}
