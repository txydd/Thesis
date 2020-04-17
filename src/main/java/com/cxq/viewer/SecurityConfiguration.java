package com.cxq.viewer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    private  AuthenticationProvider authenticationProvider;


    @Override
    protected AuthenticationManager authenticationManager()
    {
        ProviderManager authenticationManager =
                new ProviderManager(Arrays.asList(authenticationProvider)); //传入自定义认证器
        //不擦除认证密码，擦除会导致TokenBasedRememberMeServices因为找不到Credentials再调用UserDetailsService而抛出UsernameNotFoundException
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        return authenticationManager;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.headers().frameOptions().disable();

        http.csrf().disable().authorizeRequests()
                .antMatchers(  "/user/login","/user/exit","/prelogin","/index","/user/create","/register").permitAll()
                .anyRequest().access("@mySecurity.check(authentication, request)")
//                .antMatchers("/**").hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin().loginPage("/user/login").defaultSuccessUrl("/index").failureUrl("/user/login?error").loginProcessingUrl("/login");
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        super.configure(web);
        web.ignoring().antMatchers( "/assets/**"); //不拦截静态资源
    }

}
