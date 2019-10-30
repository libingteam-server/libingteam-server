package com.whu.libingteam.system.entity;

import cc.eamon.open.mapping.mapper.Mapper;
import cc.eamon.open.mapping.mapper.MapperIgnore;
import cc.eamon.open.mapping.mapper.MapperModify;
import java.io.Serializable;
import java.lang.Byte;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Mapper({"post", "update", "simple", "detail"})
public class Source implements Serializable {
  /**
   * 系统id，对外不可见，int索引 */
  private Integer id;

  /**
   * 资源类型 */
  private String type;

  /**
   * 显示标题 */
  private String title;

  /**
   * 资源链接 */
  private String link;

  /**
   * 下载量 */
  private Integer downloads;

  /**
   * 展示图片链接 */
  private String logo;

  /**
   * 创建时间 */
  @MapperIgnore({"post", "update"})
  @MapperModify(
      value = {"post", "update"},
      modify = {"modifyTime", "modifyTime"},
      recover = {"recoverTime", "recoverTime"}
  )
  private Date createTime;

  /**
   * 创建操作人 */
  @MapperIgnore({"post", "update"})
  private String createBy;

  /**
   * 更新时间 */
  @MapperIgnore({"post", "update"})
  @MapperModify(
      value = {"post", "update"},
      modify = {"modifyTime", "modifyTime"},
      recover = {"recoverTime", "recoverTime"}
  )
  private Date updateTime;

  /**
   * 更新操作人 */
  @MapperIgnore({"post", "update"})
  private String updateBy;

  /**
   * 删除状态 */
  @MapperIgnore({"post", "update"})
  private Byte isDelete;

  public Long modifyTime(Date date) {
    if (date == null) return null;
    return date.getTime();
  }

  public Date recoverTime(Long time) {
    if (time == null) return null;
    return new Date(time);
  }
}
