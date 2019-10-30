package com.whu.libingteam.system.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.system.dao.NewsDao;
import com.whu.libingteam.system.entity.News;
import com.whu.libingteam.system.entity.NewsDetailMapper;
import com.whu.libingteam.system.entity.NewsExample;
import com.whu.libingteam.system.entity.NewsPostMapper;
import com.whu.libingteam.system.entity.NewsSimpleMapper;
import com.whu.libingteam.system.entity.NewsUpdateMapper;
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
public class NewsService {
  @Autowired
  private NewsDao newsDao;

  /**
   * 分页计数函数 */
  private NewsExample initPageRowQueryFilter(Long page, Integer rows) {
    NewsExample example = new NewsExample();
    example.setOrderByClause("updateTime desc");
    NewsExample.Criteria criteria = example.createCriteria();
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
  private NewsExample initQueryFilter(Long page, Integer rows, Integer id, String type,
      Integer userid, String cover, String link, String title, String detail, Date createTime,
      String createBy, Date updateTime, String updateBy, Byte isDelete) {
    NewsExample example = new NewsExample();
    example.setOrderByClause("updateTime desc");
    NewsExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
    }
    if(type!=null && !type.equals("")) {
      criteria.andTypeEqualTo(type);
    }
    if(userid!=null) {
      criteria.andUseridEqualTo(userid);
    }
    if(cover!=null && !cover.equals("")) {
      criteria.andCoverEqualTo(cover);
    }
    if(link!=null && !link.equals("")) {
      criteria.andLinkEqualTo(link);
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

  public Long getNewsCount() throws StatusException {
    return newsDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getNewsFilterCount(Integer id, String type, Integer userid, String cover, String link,
      String title, String detail, Date createTime, String createBy, Date updateTime,
      String updateBy, Byte isDelete) throws StatusException {
    return newsDao.countByExample(initQueryFilter(0L, 0, id, type, userid, cover, link, title, detail, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public News getNewsByPrimaryKey(Integer key) throws StatusException {
    return newsDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getNewsSimpleMapByPrimaryKey(Integer key) throws StatusException {
    News entity = newsDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return NewsSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getNewsDetailMapByPrimaryKey(Integer key) throws StatusException {
    News entity = newsDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return NewsDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getNewsSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (News entity: newsDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(NewsSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getNewsDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (News entity: newsDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(NewsDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postNews(NewsPostMapper postMapper, String createBy, String updateBy)
      throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    News entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(newsDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return NewsDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postNewsList(List<NewsPostMapper> postMappers, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (NewsPostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postNews(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updateNews(NewsUpdateMapper updateMapper, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    News entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(newsDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return NewsDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updateNewsList(List<NewsUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (NewsUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updateNews(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deleteNews(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    News entity = new News();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(newsDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deleteNewsList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deleteNews(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverNews(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    News entity = new News();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(newsDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverNewsList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverNews(key, updateBy));
    }
    return count.get();
  }
}
