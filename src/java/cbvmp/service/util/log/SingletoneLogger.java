/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.util.log;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author tanbir
 *
 *
 */
public class SingletoneLogger {

    public static Log4jLoggerAdapter getLogger(Class clazz) {
        return (Log4jLoggerAdapter) LoggerFactory.getLogger(clazz);
    }

    public static String generateRequestLogString(HttpServletRequest request, String userid, String action, String result, String comment, String alarm, String optional) {
        return "[ip]:" + request.getRemoteAddr()
                + "[request_url]:" + request.getRequestURL()
                + "[method]" + request.getMethod()
                + "[params]:" + request.getQueryString()
                + "[userid]:" + userid
                + "[action]:" + action
                + "[result]:" + result
                + "[comment]:" + comment
                + "[alarm]:" + alarm
                + "[optional]:"+optional;
    }

    public static String generateRequestLogString(HttpServletRequest request) {
        return "[ip]:" + request.getRemoteAddr()
                + "[request_url]:" + request.getRequestURL()
                + "[method]" + request.getMethod()
                + "[params]:" + request.getQueryString();
    }

    public static Log4jLoggerAdapter getLogger(String appLog) {
        return (Log4jLoggerAdapter) LoggerFactory.getLogger(appLog);
    }
}
