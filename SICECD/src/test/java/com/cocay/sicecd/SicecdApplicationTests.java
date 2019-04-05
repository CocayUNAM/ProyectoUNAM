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
<<<<<<< HEAD
		TestClass usuario=new TestClass();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //usuario.setPass(passwordEncoder.encode("hola"));       
        usuario.setName("franki3");
        usuario.setPass("frank3");
        usuario.setRol("ROLE_USUARIO");
		System.out.println("\ncontraseña----"+usuario.getPass());
		_test.save(usuario);
	
	}

=======
		Genero genero=new Genero();
		genero.setGenero("masculino");
		
		_genero.save(genero);
	}
>>>>>>> 425ced035122cf746cdd9679bee2569b13ebcbad

}
