package cn.store.controller.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * spring Security 核心配置类 通用安全
 *
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ImSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * spring security -》 权限不足处理
     */
    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
        registry
                .and()
                //禁止网页iframe
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                //任何请求
                .anyRequest()
                //需要身份认证
                .permitAll()
                .and()
                //允许跨域
                .cors().configurationSource(corsConfigurationSource).and()
                //关闭跨站请求防护
                .csrf().disable();
    }

}
