package com.cocay.sicecd.step;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

public class VerificacionArchivo implements SkipPolicy{
	
	private static final Logger logger = LoggerFactory.getLogger("badRecordLogger");

	@Override
	public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
		if(exception instanceof FileNotFoundException) {
			return false;
		}else if(exception instanceof FlatFileParseException && skipCount <= 5) {
			FlatFileParseException ffpe = (FlatFileParseException) exception;
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("Ocurrió un error mientras se procesaba la linea "+ ffpe.getLineNumber()
					+ " No se agregaron los siguientes datos: \n");
			errorMessage.append(ffpe.getInput()+"\n");
			logger.error("{}",errorMessage.toString());
			return true;
		}else {
			return false;
		}
		// TODO Auto-generated method stub
	}

}
