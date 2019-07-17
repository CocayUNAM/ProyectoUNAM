package com.cocay.sicecd;
import com.cocay.sicecd.model.Estado;
import com.cocay.sicecd.model.Profesor;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.cocay.sicecd.repo.EstadoRep;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.service.SendMailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SicecdApplicationTests {

	@Autowired
	EstadoRep _estado;
	@Autowired
	ProfesorRep _profesor;
	@Autowired
	SendMailService _email;
	
	@Test
	public void contextLoads() {
		/*
		TestClass usuario=new TestClass();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //usuario.setPass(passwordEncoder.encode("hola"));       
        usuario.setName("franki4");
        usuario.setPass("frank4");
        usuario.setRol("ROLE_USUARIO");
		List<Estado> estados=_estado.findByNombre("guadalajara");
        usuario.setFk_id_estado(estados.get(0));
		_test.save(usuario);
		

	
		List<TestClass> clases=(List<TestClass>) _test.findAll();
		for (TestClass testClass : clases) {
			System.out.println(testClass.getName());
		}*/
		
		
	String from="cocayprueba@gmail.com";
	String to="puchicher@gmail.com";
	String subject="PruebaCorreo";
	String body="holas\nfrank";
	_email.sendMail(from, to, subject, body);
	
	
	}


}
