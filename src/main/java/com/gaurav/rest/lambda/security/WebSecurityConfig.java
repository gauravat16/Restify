/*
 * Copyright (c) 2018.  Gaurav Sharma, All rights reserved.
 */

package com.gaurav.rest.lambda.security;

import com.gaurav.rest.lambda.constants.ConfigurationConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${user.user1}")
    private String user;

    @Value("${password.user1}")
    private String password;

    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        password = passwordEncoder().encode(password);
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser(user).password(password)
                .roles(ConfigurationConstants.ROLE_1);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests().antMatchers("/lambda/**")
                .hasRole(ConfigurationConstants.ROLE_1).and()
                .csrf().disable().headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }
}
