package com.prodavnica.shop2.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService customUserDetailsService;

    //httpp://localhost:8080/login
    //username: alden.efendic
    //plainPassword: alden123 -> hashed1      hashed1
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //Svi koji hoće index.html -> "/"  moraju se authentificirati
                .authorizeRequests()
                .antMatchers("/")
                .hasAuthority("USER")
                .and()
                //Svi koji žele da se registruju mogu to učiniti kroz /register /saveUser
                .authorizeRequests()
                .antMatchers("/register", "/saveUser")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //Umjesto SPring Security default login stranice koristi moju i dozvoli joj pristup
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                //logout dozvoli
                .logout()
                .permitAll()
                .and()
                //basic authentication -> username, password
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
