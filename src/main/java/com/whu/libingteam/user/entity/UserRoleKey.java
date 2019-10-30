package com.whu.libingteam.user.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Long;
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
public class UserRoleKey implements Serializable {
  /**
   * 用户id */
  private Integer userid;

  /**
   * 角色id */
  private Integer roleid;

  public Long modifyTime(Date date) {
    if (date == null) return null;
    return date.getTime();
  }

  public Date recoverTime(Long time) {
    if (time == null) return null;
    return new Date(time);
  }
}
