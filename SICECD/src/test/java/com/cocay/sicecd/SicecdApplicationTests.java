package com.cocay.sicecd;
import com.cocay.sicecd.model.TestClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.cocay.sicecd.repo.TestRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SicecdApplicationTests {

	@Autowired
	TestRepository _test;
	@Test
	public void contextLoads() {

		TestClass usuario=new TestClass();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //usuario.setPass(passwordEncoder.encode("hola"));       
        usuario.setName("franki3");
        usuario.setPass("frank3");
        usuario.setRol("ROLE_USUARIO");
		System.out.println("\ncontraseña----"+usuario.getPass());
		_test.save(usuario);
	
	}



}
