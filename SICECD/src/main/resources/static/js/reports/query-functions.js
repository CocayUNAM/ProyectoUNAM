$(document).ready(function(){
    $("input[name=fecha_inicio_curso_1]").change(function(){
        var first_date = $(this).val();

        if(first_date != null){
            $("input[name=fecha_inicio_curso_2]").removeAttr("disabled");
        }else if (first_date == null){
        	$("input[name=fecha_inicio_curso_2]").attr("disabled", true);
        }
    });
});

$(document).ready(function(){
    $("input[name=fecha_fin_curso_1]").change(function(){
        var last_date = $(this).val();

        if(last_date != null){
            $("input[name=fecha_fin_curso_2]").removeAttr("disabled");
        }else if (last_date == null){
        	$("input[name=fecha_fin_curso_2]").attr("disabled", true);
        }
    });
});

$(document).ready(function(){
    $("input[name=fecha_inicio_grupo_1]").change(function(){
        var first_date = $(this).val();

        if(first_date != null){
            $("input[name=fecha_inicio_grupo_2]").removeAttr("disabled");
        }else if (first_date == null){
        	$("input[name=fecha_inicio_grupo_2]").attr("disabled", true);
        }
    });
});

$(document).ready(function(){
    $("input[name=fecha_fin_grupo_1]").change(function(){
        var last_date = $(this).val();

        if(last_date != null){
            $("input[name=fecha_fin_grupo_2]").removeAttr("disabled");
        }else if (last_date == null){
        	$("input[name=fecha_fin_grupo_2]").attr("disabled", true);
        }
    });
}); 

/*$$(document).ready( function() {
    $("#curp").change( function() {
        if ($(this).val() != null) {
        	console.log("Deshabilitando");
        	$("#nombre").prop("disabled", true);
            $("#apellido_paterno").prop("disabled", true);
            $("#rfc").prop("disabled", true);
        } else {
        	$("#nombre").prop("disabled", false);
            $("#apellido_paterno").prop("disabled", false);
            $("#rfc").prop("disabled", false);
        }
    });
});*/