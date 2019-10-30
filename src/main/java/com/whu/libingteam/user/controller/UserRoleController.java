package com.whu.libingteam.user.controller;

import cc.eamon.open.permission.annotation.Permission;
import cc.eamon.open.permission.annotation.PermissionLimit;
import cc.eamon.open.status.Status;
import cc.eamon.open.status.StatusBaseController;
import cc.eamon.open.status.StatusCode;
import com.whu.libingteam.user.entity.UserRoleKey;
import com.whu.libingteam.user.entity.UserRolePostMapper;
import com.whu.libingteam.user.entity.UserRoleUpdateMapper;
import com.whu.libingteam.user.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: eamon
 * Email: eamon@eamon.cc */
@Api(
    value = "用户角色管理",
    tags = "用户角色管理"
)
@RestController
@RequestMapping("/user/role")
@Permission
public class UserRoleController extends StatusBaseController {
  @Autowired
  private UserRoleService userRoleService;

  @ApiOperation(
      value = "获取用户角色简要列表",
      notes = "获取用户角色简要列表"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "",
      method = RequestMethod.GET
  )
  @ResponseBody
  @PermissionLimit
  public Status getUserRoleSimpleMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.getUserRoleSimpleMapList(page, rows),
        userRoleService.getUserRoleCount()
        );
  }

  @ApiOperation(
      value = "获取用户角色详情列表",
      notes = "获取用户角色详情列表"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "detail",
      method = RequestMethod.GET
  )
  @ResponseBody
  @PermissionLimit
  public Status getUserRoleDetailMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.getUserRoleDetailMapList(page, rows),
        userRoleService.getUserRoleCount()
        );
  }

  @ApiOperation(
      value = "通过主键获取用户角色简要信息",
      notes = "通过主键获取用户角色简要信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "key",
      method = RequestMethod.GET
  )
  @ResponseBody
  @PermissionLimit
  public Status getUserRoleSimpleMap(UserRoleKey key) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.getUserRoleSimpleMapByPrimaryKey(key),
        "查询成功");
  }

  @ApiOperation(
      value = "通过主键获取用户角色详细信息",
      notes = "通过主键获取用户角色详细信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "key/detail",
      method = RequestMethod.GET
  )
  @ResponseBody
  @PermissionLimit
  public Status getUserRoleDetailMap(UserRoleKey key) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.getUserRoleDetailMapByPrimaryKey(key),
        "查询成功");
  }

  @ApiOperation(
      value = "发布用户角色信息",
      notes = "发布用户角色信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "",
      method = RequestMethod.POST
  )
  @ResponseBody
  @PermissionLimit
  public Status postUserRoleMap(@RequestBody UserRolePostMapper entityPostMapper,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.postUserRole(entityPostMapper, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "发布一组用户角色信息",
      notes = "发布一组用户角色信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "list",
      method = RequestMethod.POST
  )
  @ResponseBody
  @PermissionLimit
  public Status postUserRoleMapList(@RequestBody ArrayList<UserRolePostMapper> entityPostMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.postUserRoleList(entityPostMapperList, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "更新用户角色信息",
      notes = "更新用户角色信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "key",
      method = RequestMethod.PUT
  )
  @ResponseBody
  @PermissionLimit
  public Status updateUserRoleMap(@RequestBody UserRoleUpdateMapper entityUpdateMapper,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.updateUserRole(entityUpdateMapper, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "更新一组用户角色信息",
      notes = "更新一组用户角色信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "list",
      method = RequestMethod.PUT
  )
  @ResponseBody
  @PermissionLimit
  public Status updateUserRoleMapList(@RequestBody ArrayList<UserRoleUpdateMapper> entityUpdateMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.updateUserRoleList(entityUpdateMapperList, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "删除用户角色信息",
      notes = "删除用户角色信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "key",
      method = RequestMethod.DELETE
  )
  @ResponseBody
  @PermissionLimit
  public Status deleteUserRoleByPrimaryKey(@RequestBody UserRoleKey entityKey,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.deleteUserRole(entityKey, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "删除一组用户角色信息",
      notes = "删除一组用户角色信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "list",
      method = RequestMethod.DELETE
  )
  @ResponseBody
  @PermissionLimit
  public Status deleteUserRoleByPrimaryKeyList(@RequestBody ArrayList<UserRoleKey> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.deleteUserRoleList(keys, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "恢复用户角色信息",
      notes = "恢复用户角色信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "key/recover",
      method = RequestMethod.DELETE
  )
  @ResponseBody
  @PermissionLimit
  public Status recoverUserRoleByPrimaryKey(@RequestBody UserRoleKey entityKey,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.recoverUserRole(entityKey, userName),
        "恢复成功");
  }

  @ApiOperation(
      value = "恢复一组用户角色信息",
      notes = "恢复一组用户角色信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "recover/list",
      method = RequestMethod.DELETE
  )
  @ResponseBody
  @PermissionLimit
  public Status recoverUserRoleByPrimaryKeyList(@RequestBody ArrayList<UserRoleKey> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        userRoleService.recoverUserRoleList(keys, userName),
        "恢复成功");
  }
}
