package com.whu.libingteam.system.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.system.dao.SourceDao;
import com.whu.libingteam.system.entity.Source;
import com.whu.libingteam.system.entity.SourceDetailMapper;
import com.whu.libingteam.system.entity.SourceExample;
import com.whu.libingteam.system.entity.SourcePostMapper;
import com.whu.libingteam.system.entity.SourceSimpleMapper;
import com.whu.libingteam.system.entity.SourceUpdateMapper;
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
public class SourceService {
  @Autowired
  private SourceDao sourceDao;

  /**
   * 分页计数函数 */
  private SourceExample initPageRowQueryFilter(Long page, Integer rows) {
    SourceExample example = new SourceExample();
    example.setOrderByClause("updateTime desc");
    SourceExample.Criteria criteria = example.createCriteria();
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
  private SourceExample initQueryFilter(Long page, Integer rows, Integer id, String type,
      String title, String link, Integer downloads, String logo, Date createTime, String createBy,
      Date updateTime, String updateBy, Byte isDelete) {
    SourceExample example = new SourceExample();
    example.setOrderByClause("updateTime desc");
    SourceExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
    }
    if(type!=null && !type.equals("")) {
      criteria.andTypeEqualTo(type);
    }
    if(title!=null && !title.equals("")) {
      criteria.andTitleEqualTo(title);
    }
    if(link!=null && !link.equals("")) {
      criteria.andLinkEqualTo(link);
    }
    if(downloads!=null) {
      criteria.andDownloadsEqualTo(downloads);
    }
    if(logo!=null && !logo.equals("")) {
      criteria.andLogoEqualTo(logo);
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

  public Long getSourceCount() throws StatusException {
    return sourceDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getSourceFilterCount(Integer id, String type, String title, String link,
      Integer downloads, String logo, Date createTime, String createBy, Date updateTime,
      String updateBy, Byte isDelete) throws StatusException {
    return sourceDao.countByExample(initQueryFilter(0L, 0, id, type, title, link, downloads, logo, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public Source getSourceByPrimaryKey(Integer key) throws StatusException {
    return sourceDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getSourceSimpleMapByPrimaryKey(Integer key) throws StatusException {
    Source entity = sourceDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return SourceSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getSourceDetailMapByPrimaryKey(Integer key) throws StatusException {
    Source entity = sourceDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return SourceDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getSourceSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Source entity: sourceDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(SourceSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getSourceDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Source entity: sourceDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(SourceDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postSource(SourcePostMapper postMapper, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    Source entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(sourceDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return SourceDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postSourceList(List<SourcePostMapper> postMappers,
      String createBy, String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (SourcePostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postSource(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updateSource(SourceUpdateMapper updateMapper, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Source entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(sourceDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return SourceDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updateSourceList(List<SourceUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (SourceUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updateSource(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deleteSource(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Source entity = new Source();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(sourceDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deleteSourceList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deleteSource(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverSource(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Source entity = new Source();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(sourceDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverSourceList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverSource(key, updateBy));
    }
    return count.get();
  }
}
