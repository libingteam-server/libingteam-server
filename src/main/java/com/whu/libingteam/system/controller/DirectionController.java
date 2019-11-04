package com.whu.libingteam.system.controller;

import cc.eamon.open.permission.Limit;
import cc.eamon.open.permission.annotation.Permission;
import cc.eamon.open.permission.annotation.PermissionLimit;
import cc.eamon.open.status.Status;
import cc.eamon.open.status.StatusBaseController;
import cc.eamon.open.status.StatusCode;
import com.whu.libingteam.system.entity.DirectionPostMapper;
import com.whu.libingteam.system.entity.DirectionUpdateMapper;
import com.whu.libingteam.system.service.DirectionService;
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
    value = "主研方向管理",
    tags = "主研方向管理"
)
@RestController
@RequestMapping("/direction")
@Permission
public class DirectionController extends StatusBaseController {
  @Autowired
  private DirectionService directionService;

  @ApiOperation(
      value = "获取主研方向简要列表",
      notes = "获取主研方向简要列表"
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @RequestMapping(
      value = "",
      method = RequestMethod.GET
  )
  @ResponseBody
  @PermissionLimit(limits = Limit. OPERATION_CREATE)
  public Status getDirectionSimpleMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.getDirectionSimpleMapList(page, rows),
        directionService.getDirectionCount()
        );
  }

  @ApiOperation(
      value = "获取主研方向详情列表",
      notes = "获取主研方向详情列表"
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
  public Status getDirectionDetailMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.getDirectionDetailMapList(page, rows),
        directionService.getDirectionCount()
        );
  }

  @ApiOperation(
      value = "通过主键获取主研方向简要信息",
      notes = "通过主键获取主研方向简要信息"
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
  public Status getDirectionSimpleMap(@PathVariable Integer entityKey) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.getDirectionSimpleMapByPrimaryKey(entityKey),
        "查询成功");
  }

  @ApiOperation(
      value = "通过主键获取主研方向详细信息",
      notes = "通过主键获取主研方向详细信息"
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
  public Status getDirectionDetailMap(@PathVariable Integer entityKey) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.getDirectionDetailMapByPrimaryKey(entityKey),
        "查询成功");
  }

  @ApiOperation(
      value = "发布主研方向信息",
      notes = "发布主研方向信息"
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
  public Status postDirectionMap(@RequestBody DirectionPostMapper entityPostMapper,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.postDirection(entityPostMapper, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "发布一组主研方向信息",
      notes = "发布一组主研方向信息"
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
  public Status postDirectionMapList(@RequestBody ArrayList<DirectionPostMapper> entityPostMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.postDirectionList(entityPostMapperList, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "更新主研方向信息",
      notes = "更新主研方向信息"
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
  public Status updateDirectionMap(@PathVariable Integer entityKey,
      @RequestBody DirectionUpdateMapper entityUpdateMapper, HttpServletRequest request) throws
      Exception {
    String userName = (String) request.getAttribute("userName");
    entityUpdateMapper.id = entityKey;
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.updateDirection(entityUpdateMapper, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "更新一组主研方向信息",
      notes = "更新一组主研方向信息"
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
  public Status updateDirectionMapList(@RequestBody ArrayList<DirectionUpdateMapper> entityUpdateMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.updateDirectionList(entityUpdateMapperList, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "删除主研方向信息",
      notes = "删除主研方向信息"
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
  public Status deleteDirectionByPrimaryKey(@PathVariable Integer entityKey,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.deleteDirection(entityKey, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "删除一组主研方向信息",
      notes = "删除一组主研方向信息"
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
  public Status deleteDirectionByPrimaryKeyList(@RequestBody ArrayList<Integer> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.deleteDirectionList(keys, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "恢复主研方向信息",
      notes = "恢复主研方向信息"
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
  public Status recoverDirectionByPrimaryKey(@PathVariable Integer entityKey,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.recoverDirection(entityKey, userName),
        "恢复成功");
  }

  @ApiOperation(
      value = "恢复一组主研方向信息",
      notes = "恢复一组主研方向信息"
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
  public Status recoverDirectionByPrimaryKeyList(@RequestBody ArrayList<Integer> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        directionService.recoverDirectionList(keys, userName),
        "恢复成功");
  }
}
