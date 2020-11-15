package com.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

@EnableWebSecurity
@Configuration
@Profile("dev")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Value(value = "${auth0.apiAudience}")
	private String apiAudience;
	@Value(value = "${auth0.issuer}")
	private String issuer;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    JwtWebSecurityConfigurer
        .forRS256(apiAudience, issuer)
        .configure(http)
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/v1/boats").hasAuthority("full:boat")
				.antMatchers(HttpMethod.PUT, "/api/v1/boats").hasAuthority("full:boat")
				.antMatchers(HttpMethod.GET, "/api/v1/boats").hasAuthority("full:boat")
				.antMatchers(HttpMethod.GET, "/api/v1/boats/**").hasAuthority("full:boat")
				.antMatchers(HttpMethod.DELETE, "/api/v1/boats/**").hasAuthority("full:boat")
    //    .antMatchers(HttpMethod.GET, "/api/v1/boats").permitAll()
     //   .antMatchers(HttpMethod.GET, "/api/v1/boats/**").permitAll()
        .anyRequest().authenticated();
	}

}