package com.cocay.sicecd.step;

import java.io.FileNotFoundException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;

import com.cocay.sicecd.dto.ProfesorDto;

public class VerificacionArchivo implements SkipPolicy{
	
	private static final Logger logger = LoggerFactory.getLogger("badRecordLogger");
	
	@Autowired
	private EntityManager em;

	@Override
	public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
		if(exception instanceof FileNotFoundException) {
			return false;
		}else if(exception instanceof FlatFileParseException && skipCount <= 100) {
			FlatFileParseException ffpe = (FlatFileParseException) exception;
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("Ocurrió un error mientras se procesaba la linea "+ ffpe.getLineNumber()
					+ " No se agregaron los siguientes datos: \n");
			errorMessage.append(ffpe.getInput()+"\n");
			logger.error("{}",errorMessage.toString());
			String linea = String.valueOf(ffpe.getLineNumber());
			String mensaje = "Error en la fila "+ linea + " - "+ffpe.getInput();
			List<String> lstMensajes = new ArrayList<String>();
			lstMensajes.add(mensaje);
			String consulta = "INSERT INTO errores (mensaje, estado) VALUES ('"+mensaje+"', 1)";
			Query query = em.createNativeQuery(consulta);
			query.executeUpdate();

			return true;
		}else {
			return false;
		}
		// TODO Auto-generated method stub
	}

}
