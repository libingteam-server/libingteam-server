package com.whu.libingteam.system.controller;

import cc.eamon.open.permission.annotation.Permission;
import cc.eamon.open.permission.annotation.PermissionLimit;
import cc.eamon.open.status.Status;
import cc.eamon.open.status.StatusBaseController;
import cc.eamon.open.status.StatusCode;
import com.whu.libingteam.system.entity.NewsPostMapper;
import com.whu.libingteam.system.entity.NewsUpdateMapper;
import com.whu.libingteam.system.service.NewsService;
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
    value = "新闻管理",
    tags = "新闻管理"
)
@RestController
@RequestMapping("/news")
@Permission
public class NewsController extends StatusBaseController {
  @Autowired
  private NewsService newsService;

  @ApiOperation(
      value = "获取新闻简要列表",
      notes = "获取新闻简要列表"
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
  public Status getNewsSimpleMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.getNewsSimpleMapList(page, rows),
        newsService.getNewsCount()
        );
  }

  @ApiOperation(
      value = "获取新闻详情列表",
      notes = "获取新闻详情列表"
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
  public Status getNewsDetailMapList(@RequestParam(required = false, defaultValue = "0") Long page,
      @RequestParam(required = false, defaultValue = "10") Integer rows) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.getNewsDetailMapList(page, rows),
        newsService.getNewsCount()
        );
  }

  @ApiOperation(
      value = "通过主键获取新闻简要信息",
      notes = "通过主键获取新闻简要信息"
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
  public Status getNewsSimpleMap(@PathVariable Integer entityKey) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.getNewsSimpleMapByPrimaryKey(entityKey),
        "查询成功");
  }

  @ApiOperation(
      value = "通过主键获取新闻详细信息",
      notes = "通过主键获取新闻详细信息"
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
  public Status getNewsDetailMap(@PathVariable Integer entityKey) throws Exception {
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.getNewsDetailMapByPrimaryKey(entityKey),
        "查询成功");
  }

  @ApiOperation(
      value = "发布新闻信息",
      notes = "发布新闻信息"
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
  public Status postNewsMap(@RequestBody NewsPostMapper entityPostMapper,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.postNews(entityPostMapper, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "发布一组新闻信息",
      notes = "发布一组新闻信息"
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
  public Status postNewsMapList(@RequestBody ArrayList<NewsPostMapper> entityPostMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.postNewsList(entityPostMapperList, userName, userName),
        "发布成功");
  }

  @ApiOperation(
      value = "更新新闻信息",
      notes = "更新新闻信息"
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
  public Status updateNewsMap(@PathVariable Integer entityKey,
      @RequestBody NewsUpdateMapper entityUpdateMapper, HttpServletRequest request) throws
      Exception {
    String userName = (String) request.getAttribute("userName");
    entityUpdateMapper.id = entityKey;
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.updateNews(entityUpdateMapper, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "更新一组新闻信息",
      notes = "更新一组新闻信息"
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
  public Status updateNewsMapList(@RequestBody ArrayList<NewsUpdateMapper> entityUpdateMapperList,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.updateNewsList(entityUpdateMapperList, userName),
        "更新成功");
  }

  @ApiOperation(
      value = "删除新闻信息",
      notes = "删除新闻信息"
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
  public Status deleteNewsByPrimaryKey(@PathVariable Integer entityKey, HttpServletRequest request)
      throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.deleteNews(entityKey, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "删除一组新闻信息",
      notes = "删除一组新闻信息"
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
  public Status deleteNewsByPrimaryKeyList(@RequestBody ArrayList<Integer> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.deleteNewsList(keys, userName),
        "删除成功");
  }

  @ApiOperation(
      value = "恢复新闻信息",
      notes = "恢复新闻信息"
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
  public Status recoverNewsByPrimaryKey(@PathVariable Integer entityKey, HttpServletRequest request)
      throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.recoverNews(entityKey, userName),
        "恢复成功");
  }

  @ApiOperation(
      value = "恢复一组新闻信息",
      notes = "恢复一组新闻信息"
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
  public Status recoverNewsByPrimaryKeyList(@RequestBody ArrayList<Integer> keys,
      HttpServletRequest request) throws Exception {
    String userName = (String) request.getAttribute("userName");
    return new Status(
        true,
        StatusCode.getCode("SUCCESS"),
        newsService.recoverNewsList(keys, userName),
        "恢复成功");
  }
}
