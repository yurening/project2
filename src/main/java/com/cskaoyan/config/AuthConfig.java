/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/19
 * Time: 16:04
 */
package com.cskaoyan.config;

import com.cskaoyan.filter.FormAuthFilter;
import com.cskaoyan.shiro.*;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class AuthConfig {

    //login → anon匿名
    //index → 认证之后才能访问
    //info
    //success

    /*shiroFilter*/
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        shiroFilterFactoryBean.setLoginUrl("/admin/auth/redirect");//认证失败重定向的url
        shiroFilterFactoryBean.setLoginUrl("/admin/redirect");//认证失败重定向的url
        // 配置的是拦截器 shiro提供的filter
        //这儿一定要使用linkedHashMap 否则，chain的顺序会有问题
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new FormAuthFilter());
        //第一个参数是请求url 第二个参数是过滤器
//        filterChainDefinitionMap.put("/admin/auth/login","anon");
//        filterChainDefinitionMap.put("/admin/auth/info","perms");
//        filterChainDefinitionMap.put("/admin/dashboard","perms");
////        filterChainDefinitionMap.put("/admin/stat/**", "roles");
//        filterChainDefinitionMap.put("/admin/stat/**", "authc");
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /*SecurityManager*/
    @Bean
    public DefaultWebSecurityManager securityManager(AdminRealm adminRealm, WxRealm wxRealm,
                                                     AuthSessionManager sessionManager,
                                                     AuthRealmAuthenticator authenticator,
                                                     AuthRealmAuthorticator authorticator){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setRealm(customRealm);//单个realm

        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);

        securityManager.setRealms(realms);
        //Session
        securityManager.setSessionManager(sessionManager);

        //认证分发
        securityManager.setAuthenticator(authenticator);
        //授权分发
        securityManager.setAuthorizer(authorticator);
        return securityManager;
    }


    /**
     * Shiro生命周期处理器
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /*声明式鉴权*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public AuthSessionManager sessionManager(){
        AuthSessionManager authSessionManager = new AuthSessionManager();
        authSessionManager.setDeleteInvalidSessions(true);
        authSessionManager.setGlobalSessionTimeout(600000000);
        return authSessionManager;
    }

    @Bean
    public AuthRealmAuthenticator authenticator(AdminRealm adminRealm, WxRealm wxRealm){
        AuthRealmAuthenticator authRealmAuthenticator = new AuthRealmAuthenticator();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        authRealmAuthenticator.setRealms(realms);
        return authRealmAuthenticator;
    }

    @Bean
    public AuthRealmAuthorticator authorticator(AdminRealm adminRealm, WxRealm wxRealm){
        AuthRealmAuthorticator authRealmAuthorticator = new AuthRealmAuthorticator();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        authRealmAuthorticator.setRealms(realms);
        return authRealmAuthorticator;
    }

    @Bean("mymap")
    public Map<String,Object> getMap(){
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
