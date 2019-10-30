package com.whu.libingteam.system.controller;

import cc.eamon.open.permission.annotation.Permission;
import cc.eamon.open.permission.annotation.PermissionLimit;
import cc.eamon.open.status.Status;
import cc.eamon.open.status.StatusBaseController;
import cc.eamon.open.status.StatusCode;
import com.whu.libingteam.system.entity.PaperPostMapper;
import com.whu.libingteam.system.entity.PaperUpdateMapper;
import com.whu.libingteam.system.service.PaperService;
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
    value = "论文管理",
    tags = "论文管理"
)
@RestController
@RequestMapping("/paper")
@Permission
public class PaperController extends StatusBaseController {
  @Autowired
  private PaperService paperService;

  @ApiOperation(
      value = "获取论文简要列表",
      notes = "获取论文简要列表"
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
  public Status getPaperSimpleMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.getPaperSimpleMapList(page, rows),
        paperService.getPaperCount()
        );
  }

  @ApiOperation(
      value = "获取论文详情列表",
      notes = "获取论文详情列表"
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
  public Status getPaperDetailMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.getPaperDetailMapList(page, rows),
        paperService.getPaperCount()
        );
  }

  @ApiOperation(
      value = "通过主键获取论文简要信息",
      notes = "通过主键获取论文简要信息"
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
  public Status getPaperSimpleMap(@PathVariable Integer entityKey) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.getPaperSimpleMapByPrimaryKey(entityKey),
        "查询成功");
  }

  @ApiOperation(
      value = "通过主键获取论文详细信息",
      notes = "通过主键获取论文详细信息"
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
  public Status getPaperDetailMap(@PathVariable Integer entityKey) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.getPaperDetailMapByPrimaryKey(entityKey),
        "查询成功");
  }

  @ApiOperation(
      value = "发布论文信息",
      notes = "发布论文信息"
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
  public Status postPaperMap(@RequestBody PaperPostMapper entityPostMapper,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.postPaper(entityPostMapper, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "发布一组论文信息",
      notes = "发布一组论文信息"
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
  public Status postPaperMapList(@RequestBody ArrayList<PaperPostMapper> entityPostMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.postPaperList(entityPostMapperList, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "更新论文信息",
      notes = "更新论文信息"
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
  public Status updatePaperMap(@PathVariable Integer entityKey,
      @RequestBody PaperUpdateMapper entityUpdateMapper, HttpServletRequest request) throws
      Exception {
    String userName = (String) request.getAttribute("userName");
    entityUpdateMapper.id = entityKey;
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.updatePaper(entityUpdateMapper, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "更新一组论文信息",
      notes = "更新一组论文信息"
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
  public Status updatePaperMapList(@RequestBody ArrayList<PaperUpdateMapper> entityUpdateMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.updatePaperList(entityUpdateMapperList, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "删除论文信息",
      notes = "删除论文信息"
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
  public Status deletePaperByPrimaryKey(@PathVariable Integer entityKey, HttpServletRequest request)
      throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.deletePaper(entityKey, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "删除一组论文信息",
      notes = "删除一组论文信息"
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
  public Status deletePaperByPrimaryKeyList(@RequestBody ArrayList<Integer> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.deletePaperList(keys, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "恢复论文信息",
      notes = "恢复论文信息"
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
  public Status recoverPaperByPrimaryKey(@PathVariable Integer entityKey,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.recoverPaper(entityKey, userName),
        "恢复成功");
  }

  @ApiOperation(
      value = "恢复一组论文信息",
      notes = "恢复一组论文信息"
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
  public Status recoverPaperByPrimaryKeyList(@RequestBody ArrayList<Integer> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        paperService.recoverPaperList(keys, userName),
        "恢复成功");
  }
}
