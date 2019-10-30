package com.whu.libingteam.system.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.system.dao.SubjectDao;
import com.whu.libingteam.system.entity.Subject;
import com.whu.libingteam.system.entity.SubjectDetailMapper;
import com.whu.libingteam.system.entity.SubjectExample;
import com.whu.libingteam.system.entity.SubjectPostMapper;
import com.whu.libingteam.system.entity.SubjectSimpleMapper;
import com.whu.libingteam.system.entity.SubjectUpdateMapper;
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
public class SubjectService {
  @Autowired
  private SubjectDao subjectDao;

  /**
   * 分页计数函数 */
  private SubjectExample initPageRowQueryFilter(Long page, Integer rows) {
    SubjectExample example = new SubjectExample();
    example.setOrderByClause("updateTime desc");
    SubjectExample.Criteria criteria = example.createCriteria();
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
  private SubjectExample initQueryFilter(Long page, Integer rows, Integer id, Integer teacher,
      String title, String detail, Date createTime, String createBy, Date updateTime,
      String updateBy, Byte isDelete) {
    SubjectExample example = new SubjectExample();
    example.setOrderByClause("updateTime desc");
    SubjectExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
    }
    if(teacher!=null) {
      criteria.andTeacherEqualTo(teacher);
    }
    if(title!=null && !title.equals("")) {
      criteria.andTitleEqualTo(title);
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

  public Long getSubjectCount() throws StatusException {
    return subjectDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getSubjectFilterCount(Integer id, Integer teacher, String title, String detail,
      Date createTime, String createBy, Date updateTime, String updateBy, Byte isDelete) throws
      StatusException {
    return subjectDao.countByExample(initQueryFilter(0L, 0, id, teacher, title, detail, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public Subject getSubjectByPrimaryKey(Integer key) throws StatusException {
    return subjectDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getSubjectSimpleMapByPrimaryKey(Integer key) throws StatusException {
    Subject entity = subjectDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return SubjectSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getSubjectDetailMapByPrimaryKey(Integer key) throws StatusException {
    Subject entity = subjectDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return SubjectDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getSubjectSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Subject entity: subjectDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(SubjectSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getSubjectDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Subject entity: subjectDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(SubjectDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postSubject(SubjectPostMapper postMapper, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    Subject entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(subjectDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return SubjectDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postSubjectList(List<SubjectPostMapper> postMappers,
      String createBy, String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (SubjectPostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postSubject(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updateSubject(SubjectUpdateMapper updateMapper, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Subject entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(subjectDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return SubjectDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updateSubjectList(List<SubjectUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (SubjectUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updateSubject(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deleteSubject(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Subject entity = new Subject();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(subjectDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deleteSubjectList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deleteSubject(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverSubject(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Subject entity = new Subject();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(subjectDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverSubjectList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverSubject(key, updateBy));
    }
    return count.get();
  }
}
