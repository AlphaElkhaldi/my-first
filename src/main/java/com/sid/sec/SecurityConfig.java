package com.sid.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sid.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 @Autowired
	    private JWTAuthenticationFilter jwtAuthenticationFilter;

	    @Autowired
	    private UserService userService;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    
		@Bean
	    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
	        http = http.cors().and().csrf().disable();

	        http = http
	                .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and();

	        http = http
	                .exceptionHandling()
	                .authenticationEntryPoint(
	                        ((request, response, authException) -> {
	                            System.out.println("Unauthorized request");
	                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	                        })
	                )
	                .and();

	        http
	        .authorizeHttpRequests().requestMatchers("/auth/**").permitAll()
	        .and();
	        http.authorizeHttpRequests().requestMatchers("/api/**").permitAll()
	        .and();
	        
	        
	        http.authorizeHttpRequests().requestMatchers("/api/users").hasAuthority("USER")
	        .and();
	        http.authorizeHttpRequests().anyRequest().authenticated();
	              
	        
          //  .antMatchers("/ping/**").permitAll()
	     //  .authorizeHttpRequests().requestMatchers("/ping/**").hasAuthority("USER")
         //   .anyRequest().authenticated();

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
	    }

	    @Bean
	    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
	        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	        authenticationManagerBuilder.eraseCredentials(false)
	        .userDetailsService(userService)
	                .passwordEncoder(passwordEncoder);

	        return authenticationManagerBuilder.build();
	    }

}
