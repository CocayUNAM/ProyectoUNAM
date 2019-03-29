package com.cocay.sicecd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cocay.sicecd.model.Genero;
import com.cocay.sicecd.repo.GeneroRep;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SicecdApplicationTests {

	@Autowired
	GeneroRep _genero;
	@Test
	public void contextLoads() {
		Genero genero=new Genero();
		genero.setGenero("masculino");
		
		_genero.save(genero);
	}

}
