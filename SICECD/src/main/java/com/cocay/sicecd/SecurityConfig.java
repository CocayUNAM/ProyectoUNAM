package com.cocay.sicecd;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception{
//		authenticationMgr.inMemoryAuthentication()
//		.withUser("devuser").password("{noop}dev").authorities("ROLE_USER")
//		.and()
//		.withUser("adminuser").password("{noop}admin").authorities("ROLE_USER","ROLE_ADMIN");
		
		authenticationMgr.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select name,pass from test_class where name=?")
		.authoritiesByUsernameQuery(
			"select name,rol from test_class where name=?");
	}
	
	//Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		.authorizeRequests()
		.antMatchers("/usuario*").hasRole("USUARIO")
		.antMatchers("/administrador*").hasRole("ADMIN")
		.antMatchers("/","/notprotected*").permitAll()
		.and().formLogin().loginPage("/login").permitAll()
		.and().logout().permitAll();

http.csrf().disable();
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(11);
//	}

}