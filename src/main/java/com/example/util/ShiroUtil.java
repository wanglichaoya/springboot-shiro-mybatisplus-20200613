package com.example.util;

import com.example.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2020/6/14
 * Time: 下午 04:05
 * Description:
 */
public class ShiroUtil {

    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
