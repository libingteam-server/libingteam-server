package com.whu.libingteam.system.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.system.dao.PaperDao;
import com.whu.libingteam.system.entity.Paper;
import com.whu.libingteam.system.entity.PaperDetailMapper;
import com.whu.libingteam.system.entity.PaperExample;
import com.whu.libingteam.system.entity.PaperPostMapper;
import com.whu.libingteam.system.entity.PaperSimpleMapper;
import com.whu.libingteam.system.entity.PaperUpdateMapper;
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
public class PaperService {
  @Autowired
  private PaperDao paperDao;

  /**
   * 分页计数函数 */
  private PaperExample initPageRowQueryFilter(Long page, Integer rows) {
    PaperExample example = new PaperExample();
    example.setOrderByClause("updateTime desc");
    PaperExample.Criteria criteria = example.createCriteria();
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
  private PaperExample initQueryFilter(Long page, Integer rows, Integer id, String title,
      String link, String cover, Integer clicks, Date createTime, String createBy, Date updateTime,
      String updateBy, Byte isDelete) {
    PaperExample example = new PaperExample();
    example.setOrderByClause("updateTime desc");
    PaperExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
    }
    if(title!=null && !title.equals("")) {
      criteria.andTitleEqualTo(title);
    }
    if(link!=null && !link.equals("")) {
      criteria.andLinkEqualTo(link);
    }
    if(cover!=null && !cover.equals("")) {
      criteria.andCoverEqualTo(cover);
    }
    if(clicks!=null) {
      criteria.andClicksEqualTo(clicks);
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

  public Long getPaperCount() throws StatusException {
    return paperDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getPaperFilterCount(Integer id, String title, String link, String cover,
      Integer clicks, Date createTime, String createBy, Date updateTime, String updateBy,
      Byte isDelete) throws StatusException {
    return paperDao.countByExample(initQueryFilter(0L, 0, id, title, link, cover, clicks, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public Paper getPaperByPrimaryKey(Integer key) throws StatusException {
    return paperDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getPaperSimpleMapByPrimaryKey(Integer key) throws StatusException {
    Paper entity = paperDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return PaperSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getPaperDetailMapByPrimaryKey(Integer key) throws StatusException {
    Paper entity = paperDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return PaperDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getPaperSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Paper entity: paperDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(PaperSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getPaperDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Paper entity: paperDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(PaperDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postPaper(PaperPostMapper postMapper, String createBy, String updateBy)
      throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    Paper entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(paperDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return PaperDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postPaperList(List<PaperPostMapper> postMappers, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (PaperPostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postPaper(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updatePaper(PaperUpdateMapper updateMapper, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Paper entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(paperDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return PaperDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updatePaperList(List<PaperUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (PaperUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updatePaper(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deletePaper(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Paper entity = new Paper();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(paperDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deletePaperList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deletePaper(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverPaper(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Paper entity = new Paper();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(paperDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverPaperList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverPaper(key, updateBy));
    }
    return count.get();
  }
}
