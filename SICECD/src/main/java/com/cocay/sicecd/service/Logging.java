package com.cocay.sicecd.service;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cocay.sicecd.model.Log_sys;
import com.cocay.sicecd.repo.Log_evento_sysRep;
import com.cocay.sicecd.repo.Log_sysRep;
import com.cocay.sicecd.repo.Usuario_sysRep;

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
	@Value("${host}")
	String host;
	
	

	public void logtrace(String action, String comentario){
		
		Log_sys log=new Log_sys();
		try {
			request.getUserPrincipal();
		}catch(Exception ex) {
			request=null;
		}
		if (request!=null) {
			Principal principal = request.getUserPrincipal();
			log.setIp(request.getRemoteAddr());
			if (principal==null) {
				log.setFk_id_usuario_sys(usuario.findById(1).get());
			}else {			
				log.setFk_id_usuario_sys(usuario.findByRfc(principal.getName()).get(0));
			}			
		}else {
			log.setIp(host);
			log.setFk_id_usuario_sys(usuario.findById(1).get());
		}
		log.setHora(new Date());
		log.setFk_id_log_evento_sys(evento.findById(action).get());
		if (comentario!=null){
			if(comentario.length()>1000) {
				comentario=comentario.substring(0,999);
			}
			log.setComentario(comentario);
		}
		logr.save(log);
	}

	
	public void setTrace(String action, String comentario ,String rfc) {
		Log_sys log=new Log_sys();
		log.setHora(new Date());
		ServletRequestAttributes att = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		att.getRequest().getRemoteAddr();
		log.setIp(request.getRemoteAddr());
		
			log.setFk_id_usuario_sys(usuario.findByRfc(rfc).get(0));
		
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
