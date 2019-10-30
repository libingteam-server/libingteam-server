package com.whu.libingteam.user.controller;

import cc.eamon.open.permission.annotation.Permission;
import cc.eamon.open.permission.annotation.PermissionLimit;
import cc.eamon.open.status.Status;
import cc.eamon.open.status.StatusBaseController;
import cc.eamon.open.status.StatusCode;
import com.whu.libingteam.user.entity.RolePermitKey;
import com.whu.libingteam.user.entity.RolePermitPostMapper;
import com.whu.libingteam.user.entity.RolePermitUpdateMapper;
import com.whu.libingteam.user.service.RolePermitService;
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
    value = "角色权限管理",
    tags = "角色权限管理"
)
@RestController
@RequestMapping("/role/permit")
@Permission
public class RolePermitController extends StatusBaseController {
  @Autowired
  private RolePermitService rolePermitService;

  @ApiOperation(
      value = "获取角色权限简要列表",
      notes = "获取角色权限简要列表"
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
  public Status getRolePermitSimpleMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.getRolePermitSimpleMapList(page, rows),
        rolePermitService.getRolePermitCount()
        );
  }

  @ApiOperation(
      value = "获取角色权限详情列表",
      notes = "获取角色权限详情列表"
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
  public Status getRolePermitDetailMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.getRolePermitDetailMapList(page, rows),
        rolePermitService.getRolePermitCount()
        );
  }

  @ApiOperation(
      value = "通过主键获取角色权限简要信息",
      notes = "通过主键获取角色权限简要信息"
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
  public Status getRolePermitSimpleMap(RolePermitKey key) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.getRolePermitSimpleMapByPrimaryKey(key),
        "查询成功");
  }

  @ApiOperation(
      value = "通过主键获取角色权限详细信息",
      notes = "通过主键获取角色权限详细信息"
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
  public Status getRolePermitDetailMap(RolePermitKey key) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.getRolePermitDetailMapByPrimaryKey(key),
        "查询成功");
  }

  @ApiOperation(
      value = "发布角色权限信息",
      notes = "发布角色权限信息"
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
  public Status postRolePermitMap(@RequestBody RolePermitPostMapper entityPostMapper,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.postRolePermit(entityPostMapper, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "发布一组角色权限信息",
      notes = "发布一组角色权限信息"
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
  public Status postRolePermitMapList(@RequestBody ArrayList<RolePermitPostMapper> entityPostMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.postRolePermitList(entityPostMapperList, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "更新角色权限信息",
      notes = "更新角色权限信息"
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
  public Status updateRolePermitMap(@RequestBody RolePermitUpdateMapper entityUpdateMapper,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.updateRolePermit(entityUpdateMapper, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "更新一组角色权限信息",
      notes = "更新一组角色权限信息"
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
  public Status updateRolePermitMapList(@RequestBody ArrayList<RolePermitUpdateMapper> entityUpdateMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.updateRolePermitList(entityUpdateMapperList, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "删除角色权限信息",
      notes = "删除角色权限信息"
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
  public Status deleteRolePermitByPrimaryKey(@RequestBody RolePermitKey entityKey,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.deleteRolePermit(entityKey, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "删除一组角色权限信息",
      notes = "删除一组角色权限信息"
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
  public Status deleteRolePermitByPrimaryKeyList(@RequestBody ArrayList<RolePermitKey> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.deleteRolePermitList(keys, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "恢复角色权限信息",
      notes = "恢复角色权限信息"
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
  public Status recoverRolePermitByPrimaryKey(@RequestBody RolePermitKey entityKey,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.recoverRolePermit(entityKey, userName),
        "恢复成功");
  }

  @ApiOperation(
      value = "恢复一组角色权限信息",
      notes = "恢复一组角色权限信息"
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
  public Status recoverRolePermitByPrimaryKeyList(@RequestBody ArrayList<RolePermitKey> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        rolePermitService.recoverRolePermitList(keys, userName),
        "恢复成功");
  }
}
