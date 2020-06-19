package com.example.config.shiro;

import com.example.shiro.AccountRealm;
import com.example.shiro.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2020/6/12
 * Time: 下午 04:36
 * Description: shiro启用注解拦截控制器
 * ShiroConfig，我们主要做了几件事情
 *
 * 1、引入RedisSessionDAO和RedisCacheManager，为了解决shiro的权限数据和会话信息能保存到redis中，
 *    实现会话共享。
 * 2、重写了SessionManager和DefaultWebSecurityManager，同时在DefaultWebSecurityManager中
 *    为了关闭shiro自带的session方式，我们需要设置为false，这样用户就不再能通过session方式登录shiro。
 *    后面将采用jwt凭证登录。
 * 3、在ShiroFilterChainDefinition中，我们不再通过编码形式拦截Controller访问路径，而是所有的路由
 *    都需要经过JwtFilter这个过滤器，然后判断请求头中是否含有jwt的信息，有就登录，没有就跳过。
 *    跳过之后，有Controller中的shiro注解进行再次拦截，比如@RequiresAuthentication，这样控制权限
 *    访问。
 */
@Configuration
public class ShiroConfig {

    @Autowired
    JwtFilter jwtFilter;

    /**
     * 会话是保存在redis 中的
     **/
    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        // inject redisSessionDAO
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm,
                                                     SessionManager sessionManager,
                                                     RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(accountRealm);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        defaultWebSecurityManager.setCacheManager(redisCacheManager);
        /**
         * 关闭shiro 自带的session
         * **/
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();

        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        //关闭shiro 自带的session
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        defaultSubjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);

        defaultWebSecurityManager.setSubjectDAO(defaultSubjectDAO);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/**", "jwt"); //主要通过注解方式校验权限
        defaultShiroFilterChainDefinition.addPathDefinitions(filterMap);
        return defaultShiroFilterChainDefinition;
    }


    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", jwtFilter);
        shiroFilterFactoryBean.setFilters(filters);
        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;


    }


}
