package com.relx.banking.authservice.config;

import java.net.SocketException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author Naveen.Sankhala
 * Sep 30, 2025
 */
/*
@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy
@EnableCaching
@EnableGlobalMethodSecurity(
		prePostEnabled = true,
		securedEnabled = true,
		jsr250Enabled = true)
public class JWTWebSecurityConfig {//extends WebSecurityConfigurerAdapter
	
	@Autowired
	private AppConfig appConfig;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
    
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

    	try {			
    		auth.authenticationProvider(authProvider());
    	} catch (SocketException e) {
    		e.printStackTrace();
    	}
    }
    
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception{
    	return super.authenticationManager();
    }
    
    
    @Bean
    public CorsConfiguration corsConfiguration() {
    	
    	CorsConfiguration config= new CorsConfiguration();
    	config.setAllowCredentials(true);
    	config.setAllowedOrigins(Arrays.asList(appConfig.getCorssUrl()));
    	config.setAllowedHeaders(Arrays.asList("*"));
    	config.setAllowedMethods(Arrays.asList("OPTIONS","GET","PUT","POST","DELETE"));
    	
    	return config;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf().disable()
            .cors().configurationSource(request -> new CorsConfiguration().combine(corsConfiguration())).and()
            .exceptionHandling().authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .anyRequest().authenticated();

        httpSecurity
	        .addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
	        .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
       
       httpSecurity
            .headers()
            .frameOptions().sameOrigin()  //H2 Console Needs this setting
            .cacheControl(); //disable caching
    }
    
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
            .ignoring()
            .antMatchers(
                HttpMethod.POST,
                appConfig.getGetTokenUri()
            )
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .and()
            .ignoring()
            .antMatchers(
                HttpMethod.GET,
                "/share/**" //Other Stuff You want to Ignore
            )
            .and()
            .ignoring()
            .antMatchers(
                HttpMethod.POST,
                appConfig.getLogoutTokenUri()
            )
            .and()
            .ignoring()
            .antMatchers(
                HttpMethod.POST,
                appConfig.getRefreshTokenUri()
            )
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .and()
            .ignoring().antMatchers(AUTH_WHITELIST);
    }
    
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/configuration/**",
            "/swagger-ui/**",
            "/sysAdmin/**"
    };

}
*/