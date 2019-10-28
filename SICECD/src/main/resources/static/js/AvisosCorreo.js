var stCurso = 0;
var stProfesor = 0;
var stInscripcion = 0;
var url_local = window.location.href;
var arr = url_local.split("/");
var fin = arr[0]+"//"+arr[2];

$(document).ready(function(){
	llenadoCorreos();
});

$body = $("body");

$(document).on({
    ajaxStart: function() { $body.addClass("loading");    },
     ajaxStop: function() { $body.removeClass("loading"); }    
});

function llenadoCorreos() {
	$('#tablaCorreos').bootstrapTable({
		search: false,
		pagination: true,
		pageSize: 5,
		pageList: [5, 25, 50],
        clickToSelect: false,
        singleSelect: false,
        maintainSelected: true,
        sortable: true,
        checkboxHeader: false,
        data: [],
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            return 'Mostrando ' + pageFrom + ' al ' + pageTo + ' de ' + totalRows + ' registros';
        },
        formatRecordsPerPage: function (pageNumber) {
        	return pageNumber + ' registros por p\u00e1gina';
        },
        formatLoadingMessage: function () {
        	return 'Cargando, espere por favor...';
        },
        formatSearch: function () {
        	return 'Buscar';
        },
        formatNoMatches: function () {
        	return 'No se encontr&oacute; informaci&oacute;n';
        }      
    });
}

function formatterTableCheckInput(value, row, index){
	return configCheck("checkCorreos", "checkCorreos_"+index, "validarCheckAll();");	
}

function configCheck(nameInput, nameID, nameFunction){
	return ['<input type="checkbox" ',
	        'name="'+nameInput+'"  id="'+nameID+'" ',
	        'onChange="'+nameFunction+'" /> ',
	        '<label for="'+nameID+'"></label>'
	        ].join('');
}


function checkAll(nameCheck, obj) {
	$('input[name="'+nameCheck+'"]').each(function() {
		$(this).removeAttr('checked');
		$(this).prop('checked', obj.checked);
	});
}

function validarCheckAll(){
	var bool = true;
	var checkBool = true;
	if ($('input[name="checkCorreos"]').length > 0) {
		$('input[name="checkCorreos"]').each(function(index) {
			if (!$(this).prop('checked')) {
				bool = false;
			} else {
				checkBool = false;
			}
		});
	} else {
		bool = false;
	}
	$('#checkAllCorreos').prop('checked', bool);
	
}

function btnEnviar(){
	console.log("Se envio");
}


function loadSearchResult(){
	
 $.ajax({
	  type: 'get',
	  //url: "http://localhost:8080/ejemploService/lstCorreos",
	
	  success: function(data){
		
		  /*<![CDATA[*/
		  
		  
		  $('.search_list').html(data);
		  
		  
		  /*]]>*/
		},
	  
	})
	
}

function tpFiltro(tpFiltro){
	
	switch(tpFiltro){
	case 1:
		stCurso = stCurso == 0 ? 1 : 0;
		break;
	case 2:
		stProfesor = stProfesor == 0 ? 1 : 0;
		break;
	case 3:
		stInscripcion = stInscripcion == 0 ? 1 : 0;
		break;
	
	}
	
	
}

function filtroCorreo(){
	var filtros = new Object();
	
	if(stCurso == 1){
		filtros.nombre = $("input[name='nombre_curso']").val();
		filtros.clave = $("input[name='clave_curso']").val();
		filtros.tipo = $("select[name='tipos_cursos']").val();
		filtros.fInicio = $("input[name='fecha_inicio_curso']").val();
		filtros.fTermino = $("input[name='fecha_fin_curso']").val();
	}
	
	
	if(stProfesor == 1){
		filtros.nombres = $("input[name='nombre_profesor']").val();
		filtros.rfc = $("input[name='rfc']").val();
		filtros.estado = $("select[name='estado_profesor']").val();
		filtros.turno = $("select[name='turno_profesor']").val();
	}
	
	
	if(stInscripcion == 1){
		filtros.idGrupo = $("input[name='ins_grupo']").val();
	}
	
	if(JSON.stringify(filtros) == "{}"){
		filtros = null;
	}
	
	filtrosCorreos(filtros).done(function(data){
		if(data.response != null){
			$("#tablaCorreos").bootstrapTable('load', data.response);
			successAlerta();
		}else{
			$("#tablaCorreos").bootstrapTable('load', []);
			errorAlerta();
		}
	});
	console.log(filtros);
}

function filtrosCorreos(filtros){
	return $.ajax({
		url: fin +"/rest/CorreoService/filtrosCorreos",
		type : 'POST',
		contentType: 'application/json',
		dataType: 'JSON',
		data: JSON.stringify(filtros),
		async: false
	});
}

function enviarComentario(comentario){
	return $.ajax({
		url: fin+"/rest/CorreoService/enviarComentario",
		type : 'POST',
		contentType: 'application/json',
		dataType: 'JSON',
		data: JSON.stringify(comentario),
		async: false
	});
}

function enviarSeleccionados(lstCorreos){
	return $.ajax({
		url: fin+"/rest/CorreoService/enviarCorreosSeleccionados",
		type : 'POST',
		contentType: 'application/json',
		dataType: 'JSON',
		data: JSON.stringify(lstCorreos),
		async: false
	});
}

function enviarCorreo(){
	var lstProfesores = [];
	var mns = null; // word
	mns = $('#asunto').val();
	var desc = CKEDITOR.instances.comentario.getData();
	mns.trim();
	var lstData = $("#tablaCorreos").bootstrapTable("getData");
	$(lstData).each(function(index, row){
		var bool = $("#checkCorreos_"+index).prop("checked");
		if(bool){
			row.cdAsunto = mns;
			row.cdMensaje = desc;
			lstProfesores.push(row);
		}		
	});
	
	if(lstProfesores.length != 0){
		enviarSeleccionados(lstProfesores).done(function(data) {
			if(data.response != null){
				successAlerta();
			}else{
				errorAlerta();
			}
		});
		console.log(lstProfesores);
	}
	$('#asunto').val('');
	CKEDITOR.instances.comentario.setData('');
	
}

function successAlerta(){
	swal("Completado!", " La información fue procesada correctamente!", "success");
}

function errorAlerta(){
	swal({
		  title: "Error",
		  text: "Ocurrió un error mientras se procesaba la información!",
		  icon: "warning"
		});
}

function comentarioCorreo(){
	var mensaje = new Object();
	mensaje.cdMensaje = $("input[name='comentario']").val();
	enviarComentario(mensaje).done(function(data){
		if(data.response != null){
			console.log("Se envio el comentario");
		}else{
			
		}
	})
}

