package com.eureka.ui.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eureka.ui.RetwisSecurity;
import com.eureka.ui.service.UiService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Basic interceptor that checks that each request has been authenticated against Redis.
 *
 * @author Costin Leau
 */
public class CookieInterceptor extends HandlerInterceptorAdapter {

    public static final String RETWIS_COOKIE = "retwisauth";
    public static final String RETWIS_COOKIE_USER = "retwisuser";
    public static final String RETWIS_COOKIE_USER_ID = "retwisuserid";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // all non-root requests get analyzed
        Cookie[] cookies = request.getCookies();
        String auth=null;
        String userName=null;
        String UID = null;
        if (!ObjectUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (RETWIS_COOKIE.equals(cookie.getName())) {
                    auth = cookie.getValue();
                }

                if (RETWIS_COOKIE_USER.equals(cookie.getName())) {
                    userName = cookie.getValue();
                    if (auth != null) {
                        // String [] arr = auth.split(";");
                        //String uid = arr[2];
                        //RetwisSecurity.setUser(arr[1], uid, arr[0]);
                    }

                }

                if (RETWIS_COOKIE_USER_ID.equals(cookie.getName())) {
                    UID = cookie.getValue();
                }
            }

            if (UID != null && userName != null && auth != null && !UID.isEmpty() && !userName.isEmpty() && !auth.isEmpty()){
                RetwisSecurity.setUser(userName, UID, auth);
                System.out.println("COOKIE interceptor");
            }

        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        RetwisSecurity.clean();
    }
}
