package org.scau.imterceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.scau.controller.BookController;
import org.scau.utils.JWTUtil;
import org.scau.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

// 拦截器
@Component
public class LoginInterceptor implements HandlerInterceptor {

    final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取token
        String token = request.getHeader("Authorization");

        try {
            // 将当前用户信息保存到ThreadLocal
            Map<String, Object> claim = JWTUtil.parseToken(token);
            ThreadLocalUtil.set(claim);

            logger.info("登录验证成功");
            return true;
        } catch (Exception e) {
            logger.error("登录验证失败");
            response.setStatus(401);
            return false;
        }
    }
}
