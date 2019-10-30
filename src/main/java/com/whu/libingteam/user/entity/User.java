package com.whu.libingteam.user.entity;

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
public class User implements Serializable {
  /**
   * 系统id，对外不可见，int索引 */
  private Integer id;

  /**
   * 用户登录账号 */
  private String account;

  /**
   * 密码 */
  private String password;

  /**
   * 用户名字 */
  private String name;

  /**
   * 邮箱地址 */
  private String email;

  /**
   * 用户头像图片地址 */
  private String photo;

  /**
   * 用户的联系电话 */
  private String phone;

  /**
   * 教师的职称  学生的学历 */
  private String position;

  /**
   * 入学年份 */
  @MapperModify(
      value = {"post", "update"},
      modify = {"modifyTime", "modifyTime"},
      recover = {"recoverTime", "recoverTime"}
  )
  private Date grade;

  /**
   * 是否毕业 */
  private String state;

  /**
   * 学生的自我介绍 */
  private String introducation;

  /**
   * 教师的联系地址 */
  private String address;

  /**
   * 老师的研究方向 */
  private String researchDirection;

  /**
   * salt用于密码加密 */
  private String salt;

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
