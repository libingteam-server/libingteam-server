package com.whu.libingteam.system.service;

import cc.eamon.open.status.StatusException;
import com.whu.libingteam.system.dao.PhotoDao;
import com.whu.libingteam.system.entity.Photo;
import com.whu.libingteam.system.entity.PhotoDetailMapper;
import com.whu.libingteam.system.entity.PhotoExample;
import com.whu.libingteam.system.entity.PhotoPostMapper;
import com.whu.libingteam.system.entity.PhotoSimpleMapper;
import com.whu.libingteam.system.entity.PhotoUpdateMapper;
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
public class PhotoService {
  @Autowired
  private PhotoDao photoDao;

  /**
   * 分页计数函数 */
  private PhotoExample initPageRowQueryFilter(Long page, Integer rows) {
    PhotoExample example = new PhotoExample();
    example.setOrderByClause("updateTime desc");
    PhotoExample.Criteria criteria = example.createCriteria();
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
  private PhotoExample initQueryFilter(Long page, Integer rows, Integer id, String title,
      String link, Date createTime, String createBy, Date updateTime, String updateBy,
      Byte isDelete) {
    PhotoExample example = new PhotoExample();
    example.setOrderByClause("updateTime desc");
    PhotoExample.Criteria criteria = example.createCriteria();
    if(id!=null) {
      criteria.andIdEqualTo(id);
    }
    if(title!=null && !title.equals("")) {
      criteria.andTitleEqualTo(title);
    }
    if(link!=null && !link.equals("")) {
      criteria.andLinkEqualTo(link);
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

  public Long getPhotoCount() throws StatusException {
    return photoDao.countByExample(initPageRowQueryFilter(0L, 0));
  }

  public Long getPhotoFilterCount(Integer id, String title, String link, Date createTime,
      String createBy, Date updateTime, String updateBy, Byte isDelete) throws StatusException {
    return photoDao.countByExample(initQueryFilter(0L, 0, id, title, link, createTime, createBy, updateTime, updateBy, isDelete));
  }

  public Photo getPhotoByPrimaryKey(Integer key) throws StatusException {
    return photoDao.selectByPrimaryKey(key);
  }

  public Map<String, Object> getPhotoSimpleMapByPrimaryKey(Integer key) throws StatusException {
    Photo entity = photoDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return PhotoSimpleMapper.getMap(entity);
  }

  public Map<String, Object> getPhotoDetailMapByPrimaryKey(Integer key) throws StatusException {
    Photo entity = photoDao.selectByPrimaryKey(key);
    if(entity == null) throw new StatusException("ENTITY_NULL");
    return PhotoDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> getPhotoSimpleMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Photo entity: photoDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(PhotoSimpleMapper.getMap(entity));
    }
    return entityMapList;
  }

  public List<Map<String, Object>> getPhotoDetailMapList(Long page, Integer rows) throws
      StatusException {
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    for (Photo entity: photoDao.selectByExample(initPageRowQueryFilter(page, rows))) {
      entityMapList.add(PhotoDetailMapper.getMap(entity));
    }
    return entityMapList;
  }

  public Map<String, Object> postPhoto(PhotoPostMapper postMapper, String createBy, String updateBy)
      throws StatusException {
    // 记录上传条目数;
    AtomicInteger count = new AtomicInteger();
    // 从上传数据中拿出记录;
    Photo entity = postMapper.getEntity();
    // 记录创建者;
    entity.setCreateBy(createBy);
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    // 持久化对象;
    try {
      count.addAndGet(photoDao.insertSelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_EXIST");
    }
    return PhotoDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> postPhotoList(List<PhotoPostMapper> postMappers, String createBy,
      String updateBy) throws StatusException {
    // 记录上传条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity均上传;
    for (PhotoPostMapper postMapper: postMappers) {
      // 插入计数;
      entityMapList.add(postPhoto(postMapper, createBy, updateBy));
    }
    return entityMapList;
  }

  public Map<String, Object> updatePhoto(PhotoUpdateMapper updateMapper, String updateBy) throws
      StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Photo entity = updateMapper.getEntity();
    // 记录更新者;
    entity.setUpdateBy(updateBy);
    try {
      count.addAndGet(photoDao.updateByPrimaryKeySelective(entity));
    }
    catch (Exception e) {
      throw new StatusException("ENTITY_NULL");
    }
    return PhotoDetailMapper.getMap(entity);
  }

  public List<Map<String, Object>> updatePhotoList(List<PhotoUpdateMapper> updateMappers,
      String updateBy) throws StatusException {
    // 记录更新条目数;
    List<Map<String, Object>> entityMapList = new ArrayList<>();
    // 将List中所有的Entity更新;
    for (PhotoUpdateMapper updateMapper: updateMappers) {
      // 更新计数;
      entityMapList.add(updatePhoto(updateMapper, updateBy));
    }
    return entityMapList;
  }

  public Integer deletePhoto(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Photo entity = new Photo();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 删除条目;
    entity.setIsDelete((byte) 1);
    count.addAndGet(photoDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer deletePhotoList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(deletePhoto(key, updateBy));
    }
    return count.get();
  }

  public Integer recoverPhoto(Integer key, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 从更新数据中拿出记录;
    Photo entity = new Photo();
    // 记录更新者;
    entity.setId(key);
    entity.setUpdateBy(updateBy);
    // 恢复条目;
    entity.setIsDelete((byte) 0);
    count.addAndGet(photoDao.updateByPrimaryKeySelective(entity));
    return count.get();
  }

  public Integer recoverPhotoList(List<Integer> keys, String updateBy) throws StatusException {
    // 记录更新条目数;
    AtomicInteger count = new AtomicInteger();
    // 将List中所有的Entity更新;
    for (Integer key: keys) {
      // 更新计数;
      count.addAndGet(recoverPhoto(key, updateBy));
    }
    return count.get();
  }
}
