package com.brain.security;


import com.brain.model.LoginUser;
import com.brain.security.authentication.MyAuthenctiationFailureHandler;
import com.brain.security.authentication.MyAuthenticationSuccessHandler;
import com.brain.security.exception.RestAuthenticationAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //关闭跨站伪造
        httpSecurity.csrf().disable();

        //开始请求权限配置
        httpSecurity.authorizeRequests()
                .antMatchers("/login.html", //列出不想被验证拦截的页面
                        "/my/**",
                        "/treetable-lay/**",
                        "/xadmin/**",
                        "/ztree/**",
                        "/statics/**"
                        )
                .permitAll()
                .anyRequest()
                .authenticated()
                ;
        //解决X-Frame-Options DENY问题
        httpSecurity.headers().frameOptions().sameOrigin();
        httpSecurity.formLogin()
                .loginPage("/login.html")  //指定登陆页面
                .loginProcessingUrl("/login") //指定请求的url地址
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenctiationFailureHandler)
                .and().logout().permitAll().invalidateHttpSession(true) //登出功能
                .deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler()) //删除cookie
        ;
        //异常处理
        httpSecurity.exceptionHandling().accessDeniedHandler(restAuthenticationAccessDeniedHandler);
    }


    //设置密码加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                try {
                    LoginUser user = (LoginUser) authentication.getPrincipal();

                } catch (Exception e) {
                }
                httpServletResponse.sendRedirect("/login");
            }
        };
    }
}
