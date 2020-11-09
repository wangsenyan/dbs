package com.dbs.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 此文件作为文档使用
 * 1. 引入SpringSecurity Starter
 * 2. 编写SpringSecurity的配置类
 *
 * @EnableWebSecurity extends WebSecurityConfigurerAdapter
 * 3. 控制请求的访问权限 Ctrl+O 能重写的方法
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    //    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http.authorizeRequests()
                .antMatchers("/order/**").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");
        /**
         * 开启自动配置的登录功能
         * 1. /login来到登录页面
         * 2. 重定向到/login?error表示登录失败
         * 3. 更多详细规则
         */
        http.formLogin();
        /**
         * 开启自动配置的注销功能
         * 访问 /logout表示用户注销,清空session
         * 注销成功返回 /login?logout页面
         */

        http.logout().logoutSuccessUrl("/");//注销成功返回路径 /
        http.rememberMe();//记住我功能
    }

    /**
     * 定义认证规则
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder()) //加密方式
                .withUser("zhangsan").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1", "VIP2")
                .and()
                .withUser("lisi").password(new BCryptPasswordEncoder().encode("111111")).roles("VIP2", "VIP3")
                .and()
                .withUser("wangwu").password(new BCryptPasswordEncoder().encode("888888")).roles("VIP3", "VIP1");

    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
