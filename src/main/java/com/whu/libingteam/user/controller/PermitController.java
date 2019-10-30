package com.whu.libingteam.user.controller;

import cc.eamon.open.permission.annotation.Permission;
import cc.eamon.open.permission.annotation.PermissionLimit;
import cc.eamon.open.status.Status;
import cc.eamon.open.status.StatusBaseController;
import cc.eamon.open.status.StatusCode;
import com.whu.libingteam.user.entity.PermitPostMapper;
import com.whu.libingteam.user.entity.PermitUpdateMapper;
import com.whu.libingteam.user.service.PermitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
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
    value = "权限管理",
    tags = "权限管理"
)
@RestController
@RequestMapping("/permit")
@Permission
public class PermitController extends StatusBaseController {
  @Autowired
  private PermitService permitService;

  @ApiOperation(
      value = "获取权限简要列表",
      notes = "获取权限简要列表"
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
  public Status getPermitSimpleMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.getPermitSimpleMapList(page, rows),
        permitService.getPermitCount()
        );
  }

  @ApiOperation(
      value = "获取权限详情列表",
      notes = "获取权限详情列表"
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
  public Status getPermitDetailMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.getPermitDetailMapList(page, rows),
        permitService.getPermitCount()
        );
  }

  @ApiOperation(
      value = "通过主键获取权限简要信息",
      notes = "通过主键获取权限简要信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "{entityKey}",
      method = RequestMethod.GET
  )
  @ResponseBody
  @PermissionLimit
  public Status getPermitSimpleMap(@PathVariable Integer entityKey) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.getPermitSimpleMapByPrimaryKey(entityKey),
        "查询成功");
  }

  @ApiOperation(
      value = "通过主键获取权限详细信息",
      notes = "通过主键获取权限详细信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "{entityKey}/detail",
      method = RequestMethod.GET
  )
  @ResponseBody
  @PermissionLimit
  public Status getPermitDetailMap(@PathVariable Integer entityKey) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.getPermitDetailMapByPrimaryKey(entityKey),
        "查询成功");
  }

  @ApiOperation(
      value = "发布权限信息",
      notes = "发布权限信息"
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
  public Status postPermitMap(@RequestBody PermitPostMapper entityPostMapper,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.postPermit(entityPostMapper, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "发布一组权限信息",
      notes = "发布一组权限信息"
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
  public Status postPermitMapList(@RequestBody ArrayList<PermitPostMapper> entityPostMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.postPermitList(entityPostMapperList, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "更新权限信息",
      notes = "更新权限信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "{entityKey}",
      method = RequestMethod.PUT
  )
  @ResponseBody
  @PermissionLimit
  public Status updatePermitMap(@PathVariable Integer entityKey,
      @RequestBody PermitUpdateMapper entityUpdateMapper, HttpServletRequest request) throws
      Exception {
    String userName = (String) request.getAttribute("userName");
    entityUpdateMapper.id = entityKey;
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.updatePermit(entityUpdateMapper, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "更新一组权限信息",
      notes = "更新一组权限信息"
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
  public Status updatePermitMapList(@RequestBody ArrayList<PermitUpdateMapper> entityUpdateMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.updatePermitList(entityUpdateMapperList, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "删除权限信息",
      notes = "删除权限信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "{entityKey}",
      method = RequestMethod.DELETE
  )
  @ResponseBody
  @PermissionLimit
  public Status deletePermitByPrimaryKey(@PathVariable Integer entityKey,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.deletePermit(entityKey, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "删除一组权限信息",
      notes = "删除一组权限信息"
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
  public Status deletePermitByPrimaryKeyList(@RequestBody ArrayList<Integer> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.deletePermitList(keys, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "恢复权限信息",
      notes = "恢复权限信息"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "{entityKey}/recover",
      method = RequestMethod.DELETE
  )
  @ResponseBody
  @PermissionLimit
  public Status recoverPermitByPrimaryKey(@PathVariable Integer entityKey,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.recoverPermit(entityKey, userName),
        "恢复成功");
  }

  @ApiOperation(
      value = "恢复一组权限信息",
      notes = "恢复一组权限信息"
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
  public Status recoverPermitByPrimaryKeyList(@RequestBody ArrayList<Integer> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        permitService.recoverPermitList(keys, userName),
        "恢复成功");
  }
}
