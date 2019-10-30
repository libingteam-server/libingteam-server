package com.whu.libingteam.system.controller;

import cc.eamon.open.permission.annotation.Permission;
import cc.eamon.open.permission.annotation.PermissionLimit;
import cc.eamon.open.status.Status;
import cc.eamon.open.status.StatusBaseController;
import cc.eamon.open.status.StatusCode;
import com.whu.libingteam.system.entity.ResearchPostMapper;
import com.whu.libingteam.system.entity.ResearchUpdateMapper;
import com.whu.libingteam.system.service.ResearchService;
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
    value = "科研成果管理",
    tags = "科研成果管理"
)
@RestController
@RequestMapping("/research")
@Permission
public class ResearchController extends StatusBaseController {
  @Autowired
  private ResearchService researchService;

  @ApiOperation(
      value = "获取科研成果简要列表",
      notes = "获取科研成果简要列表"
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
  public Status getResearchSimpleMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.getResearchSimpleMapList(page, rows),
        researchService.getResearchCount()
        );
  }

  @ApiOperation(
      value = "获取科研成果详情列表",
      notes = "获取科研成果详情列表"
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
  public Status getResearchDetailMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.getResearchDetailMapList(page, rows),
        researchService.getResearchCount()
        );
  }

  @ApiOperation(
      value = "通过主键获取科研成果简要信息",
      notes = "通过主键获取科研成果简要信息"
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
  public Status getResearchSimpleMap(@PathVariable Integer entityKey) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.getResearchSimpleMapByPrimaryKey(entityKey),
        "查询成功");
  }

  @ApiOperation(
      value = "通过主键获取科研成果详细信息",
      notes = "通过主键获取科研成果详细信息"
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
  public Status getResearchDetailMap(@PathVariable Integer entityKey) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.getResearchDetailMapByPrimaryKey(entityKey),
        "查询成功");
  }

  @ApiOperation(
      value = "发布科研成果信息",
      notes = "发布科研成果信息"
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
  public Status postResearchMap(@RequestBody ResearchPostMapper entityPostMapper,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.postResearch(entityPostMapper, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "发布一组科研成果信息",
      notes = "发布一组科研成果信息"
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
  public Status postResearchMapList(@RequestBody ArrayList<ResearchPostMapper> entityPostMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.postResearchList(entityPostMapperList, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "更新科研成果信息",
      notes = "更新科研成果信息"
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
  public Status updateResearchMap(@PathVariable Integer entityKey,
      @RequestBody ResearchUpdateMapper entityUpdateMapper, HttpServletRequest request) throws
      Exception {
    String userName = (String) request.getAttribute("userName");
    entityUpdateMapper.id = entityKey;
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.updateResearch(entityUpdateMapper, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "更新一组科研成果信息",
      notes = "更新一组科研成果信息"
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
  public Status updateResearchMapList(@RequestBody ArrayList<ResearchUpdateMapper> entityUpdateMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.updateResearchList(entityUpdateMapperList, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "删除科研成果信息",
      notes = "删除科研成果信息"
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
  public Status deleteResearchByPrimaryKey(@PathVariable Integer entityKey,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.deleteResearch(entityKey, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "删除一组科研成果信息",
      notes = "删除一组科研成果信息"
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
  public Status deleteResearchByPrimaryKeyList(@RequestBody ArrayList<Integer> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.deleteResearchList(keys, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "恢复科研成果信息",
      notes = "恢复科研成果信息"
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
  public Status recoverResearchByPrimaryKey(@PathVariable Integer entityKey,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.recoverResearch(entityKey, userName),
        "恢复成功");
  }

  @ApiOperation(
      value = "恢复一组科研成果信息",
      notes = "恢复一组科研成果信息"
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
  public Status recoverResearchByPrimaryKeyList(@RequestBody ArrayList<Integer> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        researchService.recoverResearchList(keys, userName),
        "恢复成功");
  }
}
