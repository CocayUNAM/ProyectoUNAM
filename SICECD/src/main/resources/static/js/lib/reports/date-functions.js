$(document).ready(function(){
    $("input[name=fecha_inicio_curso_1]").change(function(){
        var first_date = $(this).val();

        if(first_date != null){
            $("input[name=fecha_inicio_curso_2]").removeAttr("disabled");
        }else if (first_date == null){
        	$("input[name=fecha_inicio_curso_2]").attr("disabled", true);
        }
    });
    
    $("input[name=fecha_fin_curso_1]").change(function(){
        var last_date = $(this).val();

        if(last_date != null){
            $("input[name=fecha_fin_curso_2]").removeAttr("disabled");
        }else if (last_date == null){
        	$("input[name=fecha_fin_curso_2]").attr("disabled", true);
        }
    });
    
    $("input[name=fecha_inicio_grupo_1]").change(function(){
        var first_date = $(this).val();

        if(first_date != null){
            $("input[name=fecha_inicio_grupo_2]").removeAttr("disabled");
        }else if (first_date == null){
        	$("input[name=fecha_inicio_grupo_2]").attr("disabled", true);
        }
    });
    
    $("input[name=fecha_fin_grupo_1]").change(function(){
        var last_date = $(this).val();

        if(last_date != null){
            $("input[name=fecha_fin_grupo_2]").removeAttr("disabled");
        }else if (last_date == null){
        	$("input[name=fecha_fin_grupo_2]").attr("disabled", true);
        }
    });
});