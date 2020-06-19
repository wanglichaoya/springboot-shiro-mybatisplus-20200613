package com.example.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2020/6/12
 * Time: 上午 10:54
 * Description:
 */
@Configuration
// 使用注解 @EnableTransactionManagement 开启事务支持
@EnableTransactionManagement
// 通过@mapperScan注解指定要变成实现类的接口所在的包，然后包下面的所有接口在编译之后都会生成相应的实现类。
@MapperScan("com.example.mapper")
public class MybatisPlusConfig {

    // 定义一个分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
