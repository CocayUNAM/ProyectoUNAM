package com.cocay.sicecd.service;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cocay.sicecd.model.Usuario_sys;
import com.cocay.sicecd.repo.Usuario_sysRep;

@Service
public class MyAppUserDetailsService implements UserDetailsService {
	@Autowired
	private Usuario_sysRep usuarioSys;
	
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		Usuario_sys activeUserInfo = usuarioSys.findByRfc(userName).get(0);
		GrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getPerfil_sys().getNombre());
		UserDetails userDetails = (UserDetails)new User(activeUserInfo.getRfc(),
				activeUserInfo.getPassword(), Arrays.asList(authority));
		return userDetails;
	}
}