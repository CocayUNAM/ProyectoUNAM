package com.cocay.sicecd;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.cocay.sicecd.service.MyAppUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private MyAppUserDetailsService myAppUserDetailsService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/login", "/css/**", "/fonts/**", "/img/**", "/js/**", "favicon.ico").permitAll()
                .anyRequest().authenticated()
            .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/table-basic")
                .permitAll()
            .and().logout()//logout configuration
        		.logoutUrl("/logout") 
        		.logoutSuccessUrl("/login")
        		.permitAll();
    }
	
	//*
	@Autowired
   	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
              auth.userDetailsService(myAppUserDetailsService).passwordEncoder(passwordEncoder);
	}
	//*/
	
	/*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("{noop}password").roles("USER");
	}
	//*/
	
	/*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception{
//		authenticationMgr.inMemoryAuthentication()
//		.withUser("devuser").password("{noop}dev").authorities("ROLE_USER")
//		.and()
//		.withUser("adminuser").password("{noop}admin").authorities("ROLE_USER","ROLE_ADMIN");
		
		authenticationMgr.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select rfc, password from Usuario_sys where rfc=?")
		.authoritiesByUsernameQuery(
			"select rfc, fk_id_perfil_sys from Usuario_sys where rfc=?");
	}
	//*/

	/*
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
	//*/
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(11);
//	}

}