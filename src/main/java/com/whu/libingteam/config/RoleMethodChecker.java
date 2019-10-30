package com.whu.libingteam.config;

import cc.eamon.open.permission.mvc.DefaultChecker;
import cc.eamon.open.status.Status;
import cc.eamon.open.status.StatusException;
import com.alibaba.fastjson.JSON;
import com.whu.libingteam.user.service.UserService;
import com.whu.libingteam.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class RoleMethodChecker extends DefaultChecker {


    private boolean isDebug = false;


    @Autowired
    private UserService userService;


    /**
     * 在这里从request拿到UserId
     * 查出User所拥有的角色
     * 返回即可
     *
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public Object handleException(HttpServletResponse response, Exception ex) {
        if (ex instanceof StatusException) {
            return JSON.toJSONString(StatusException.procExcp(ex));
        }
        return JSON.toJSONString(new Status(false, "PERMISSION_LOW"));
    }


    /**
     * 添加跨域请求头
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        // 允许的访问方法
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "token, userId, Origin, X-Requested-With, Content-Type, Accept");

        response.setHeader("Access-Control-Expose-Headers", "token, userId");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        if (request.getMethod().equals("OPTIONS")) {
            //跨域资源共享标准新增了一组 HTTP 首部字段，允许服务器声明哪些源站有权限访问哪些资源。
            // 另外，规范要求，对那些可能对服务器数据产生副作用的 HTTP 请求方法（特别是 GET 以外的 HTTP 请求，或者搭配某些 MIME 类型的 POST 请求），
            // 浏览器必须首先使用 OPTIONS 方法发起一个预检请求（preflight request），
            // 从而获知服务端是否允许该跨域请求。服务器确认允许之后，才发起实际的 HTTP 请求。
            // 在预检请求的返回中，服务器端也可以通知客户端，是否需要携带身份凭证（包括 Cookies 和 HTTP 认证相关数据）。
            // 参考：https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Access_control_CORS
            response.setStatus(HttpServletResponse.SC_OK);
        }
        return true;
    }


    @Override
    public boolean preCheck(HttpServletRequest request, HttpServletResponse response, Object... args) throws Exception {
        try {

            String token = request.getHeader("token");//header方式
            String userId = request.getHeader("userId");//header方式

            if (null == token || token.isEmpty()) {
                for (Cookie c : request.getCookies()) {
                    if (c.getName().equals("token")) {
                        token = c.getValue();
                    }
                    if (c.getName().equals("userId")) {
                        userId = c.getValue();
                    }
                }
            }


            int id = Integer.parseInt(userId);

            Map<String, Object> user = userService.getUserDetailMapByPrimaryKey(id);
            if ((JedisUtil.hasKey(String.valueOf(id))) && (JedisUtil.get(String.valueOf(id))).equals(token)) {
                request.setAttribute("permits", user.get("permits"));
                request.setAttribute("userName", user.get("name"));
                return true;
            } else {
                throw new StatusException("UNLOGIN");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new StatusException("UNLOGIN");
        }
    }

    @Override
    public boolean checkClass(HttpServletRequest request, HttpServletResponse response, Class clazz, String value, Object... args) throws Exception {
        if (value.equals("")) return true;
        List permits = (List) request.getAttribute("permits");
        if (permits != null) {
            for (int i = 0; i < permits.size(); i++) {
                Map permit = (Map) permits.get(i);
                if (permit != null) {
                    String data = (String) permit.get("sysId");
                    if (data != null && value.contains(data)) {
                        request.setAttribute("permit", permit);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkInterface(HttpServletRequest request, HttpServletResponse response, Method method, String value, Object... args) throws Exception {
        if (args.length == 0) return true;
        Map o = (Map) request.getAttribute("permit");
        for (Object a : args) {
            if (((String)o.get("operation")).contains(
                    ((String) a).split("_")[1]
            )) {
                return true;
            }
        }
        return false;
    }
}
