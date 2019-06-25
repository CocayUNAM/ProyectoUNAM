package com.cocay.sicecd.service;

import com.cocay.sicecd.model.Log_evento_sys;
import com.cocay.sicecd.model.Log_sys;
import com.cocay.sicecd.repo.Log_evento_sysRep;
import com.cocay.sicecd.repo.Log_sysRep;
import com.cocay.sicecd.repo.Usuario_sysRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

@Service
public class Logging {
	@Autowired
	HttpServletRequest request;
	@Autowired
	Usuario_sysRep usuario;
	@Autowired
	Log_sysRep logr;
	@Autowired
	Log_evento_sysRep evento;

	public void logtrace(String action, String comentario){
		Principal principal = request.getUserPrincipal();
		Log_sys log=new Log_sys();
		log.setHora(new Date());
		log.setIp(request.getRemoteAddr());
		log.setFk_id_usuario_sys(usuario.findByRfc(principal.getName()).get(0));
		log.setFk_id_log_evento_sys(evento.findById(action).get());

		if (comentario!=null){
			log.setComentario(comentario);
		}
		logr.save(log);


	}



	public void setTrace(String action) {


	logtrace(action,null);

	}





	public void setTrace(String action, String comments) {

		logtrace(action,comments);
	}

}
