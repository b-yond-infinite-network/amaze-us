package com.mg.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mg.challenge.services.CustomUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Override
//	protected AuthenticationManager authenticationManager() throws Exception {
//		// TODO Auto-generated method stub
//		return super.authenticationManager();
//	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		// In Memory Auth
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).authorities("USER",
				"ADMIN");

		// Database Auth
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception
    {
    	http.httpBasic();
//    	http.formLogin();
    	http.cors();
    	
    	http.authorizeRequests()
//	        .antMatchers("/").permitAll()
	        .antMatchers("/h2-console/**").permitAll();
    	
//    	http.authorizeRequests().anyRequest().permitAll();
    	http.authorizeRequests().anyRequest().authenticated();

		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		
//		http.httpBasic().authenticationEntryPoint((request, response, e) -> 
//	    {
//	        response.setContentType("application/json;charset=UTF-8");
//	        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//	        response.getWriter().write(new JSONObject() 
//	                .put("timestamp", LocalDateTime.now())
//	                .put("message", "Access denied")
//	                .toString());
//	    });
    }
}
