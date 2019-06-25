package com.cocay.sicecd.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cocay.sicecd.dto.FiltroCorreoDTO;
import com.cocay.sicecd.dto.ProfesorDto;
import com.cocay.sicecd.service.SendMailService;

@SuppressWarnings("deprecation")
@Component
public class AvisosCorreoDAO {

	@Autowired
	private EntityManager em;
	
	@Autowired
	SendMailService _email;
	
	public List<ProfesorDto> envioCorreos(List<ProfesorDto> profesorDTO) {
		List<ProfesorDto> lstProfesores = new ArrayList<ProfesorDto>();
		lstProfesores = profesorDTO;
		for (ProfesorDto p : lstProfesores) {
			System.out.print(p.getCorreo());
			String from="cocayprueba@gmail.com";
			String to=p.getCorreo();
			String subject="PruebaCorreo";
			String body=" FELICIDADES !!!! MEJORA TUS CONOCIMIENTOS E INSCRIBETE A LOS CURSOS QUE TENEMOS PARA TI... ENTRA AL SIGUIENTE LINK PARA MÁS INFORMACIÓN";
			_email.sendMail(from, to, subject, body);
		}
		return lstProfesores;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProfesorDto> filtrosCorreos(FiltroCorreoDTO filtroCorreoDTO){
		List<ProfesorDto> lstProfesores = new ArrayList<ProfesorDto>();
		boolean bool = false;
		String consulta = "SELECT nombres, correo FROM (SELECT \n" + 
				"   PROF.pk_id_profesor idProfesor,\n" +
				"	PROF.NOMBRE ||' '|| PROF.apellido_paterno ||' '|| PROF.apellido_materno nombres,\n" + 
				"	PROF.CORREO correo,\n" + 
				"	CUR.CLAVE CLAVE,\n" + 
				"	CUR.NOMBRE NOMBRE,\n" + 
				"	CUR.FK_ID_TIPO_CURSO FK_ID_TIPO_CURSO,\n" + 
				"	CUR.F_INICIO F_INICIO,\n" + 
				"	CUR.F_TERMINO F_TERMINO,\n" + 
				"	PROF.rfc rfc,\n" + 
				"	PROF.fk_id_estado fk_id_estado,\n" + 
				"	PROF.fk_id_turno fk_id_turno,\n" + 
				"	INS.fk_id_grupo fk_id_grupo\n" + 
				"FROM INSCRIPCION INS \n" + 
				"	INNER JOIN PROFESOR PROF ON PROF.pk_id_profesor = INS.fk_id_profesor\n" + 
				"	INNER JOIN GRUPO GRP ON INS.fk_id_grupo = GRP.pk_id_grupo\n" + 
				"	INNER JOIN CURSO CUR ON GRP.fk_id_curso = CUR.pk_id_curso) PROFESORES";
		
		if(filtroCorreoDTO != null) {
			consulta += " WHERE ";
			if(filtroCorreoDTO.getNombre() != null && !filtroCorreoDTO.getNombre().equals("")) {
				consulta += " NOMBRE LIKE '%"+filtroCorreoDTO.getNombre()+"%'";
				bool = true;
			}
			
			if(filtroCorreoDTO.getClave() != null && !filtroCorreoDTO.getClave().equals("")) {
				consulta += (bool ? " OR " : " " )+"CLAVE LIKE '%"+filtroCorreoDTO.getClave()+"%'";
				bool = true;
			}
			
			if(filtroCorreoDTO.getTipo() != null && !filtroCorreoDTO.getTipo().equals("")) {
				consulta += (bool ? " OR " : " " )+" FK_ID_TIPO_CURSO = "+filtroCorreoDTO.getTipo()+"";
				bool = true;
			}
			
			if(filtroCorreoDTO.getfInicio() != null && !filtroCorreoDTO.getfInicio().equals("")) {
				consulta += (bool ? " OR " : " " )+" TO_CHAR( F_INICIO, 'YYYY-MM-DD') = '"+filtroCorreoDTO.getfInicio()+"'";
				bool = true;
			}
			
			if(filtroCorreoDTO.getfTermino() != null && !filtroCorreoDTO.getfTermino().equals("")) {
				consulta +=  (bool ? " OR " : " " )+" TO_CHAR( F_TERMINO, 'YYYY-MM-DD') = '"+filtroCorreoDTO.getfTermino()+"'";
				bool = true;
			}
			
			if(filtroCorreoDTO.getNombres() != null && !filtroCorreoDTO.getNombres().equals("")) {
				consulta += (bool ? " OR " : " " )+" NOMBRES LIKE '%"+filtroCorreoDTO.getNombres()+"%'";
				bool = true;
			}
			
			if(filtroCorreoDTO.getRfc() != null && !filtroCorreoDTO.getRfc().equals("")) {
				consulta += (bool ? " OR " : " " )+" rfc = '"+filtroCorreoDTO.getRfc()+"'";
				bool = true;
			}
			
			if(filtroCorreoDTO.getEstado() != null && !filtroCorreoDTO.getEstado().equals("")) {
				consulta += (bool ? " OR " : " " )+" fk_id_estado = "+ filtroCorreoDTO.getEstado()+"";
				bool = true;
			}
			
			if(filtroCorreoDTO.getTurno() != null && !filtroCorreoDTO.getTurno().equals("")) {
				consulta += (bool ? " OR " : " " )+" fk_id_turno = "+ filtroCorreoDTO.getTurno()+"";
				bool = true;
			}
			
			if(filtroCorreoDTO.getIdGrupo() != null && !filtroCorreoDTO.getIdGrupo().equals("")) {
				consulta += (bool ? " OR " : " " )+" fk_id_grupo = "+ filtroCorreoDTO.getIdGrupo()+"";
				bool = true;
			}
			
		}
		
		
		Query query = em.createNativeQuery(consulta);
		query.unwrap(SQLQuery.class)
		.addScalar("nombres", StringType.INSTANCE)
		.addScalar("correo", StringType.INSTANCE)
		.setResultTransformer(Transformers.aliasToBean(ProfesorDto.class));
		lstProfesores = (List<ProfesorDto>) query.getResultList();
		
		return lstProfesores;
	}
}
