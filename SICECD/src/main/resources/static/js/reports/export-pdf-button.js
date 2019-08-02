/*
 * Función que transforma una tabla HTML a un objeto JS.
 */
function tableToJs(table) {
	var data = [];

    var headers = [];

    for (var i=0; i<table.rows[0].cells.length; i++) {
        headers[i] = table.rows[0].cells[i].innerHTML.toLowerCase().replace(/ /gi,'');
    }
    
    data.push(headers);
    
    for (var i=1; i<table.rows.length; i++) {

        var tableRow = table.rows[i];
        var rowData = {};

        for (var j=0; j<tableRow.cells.length; j++) {
        	if (!tableRow.cells[j].innerHTML) {
        		rowData[headers[j]] = "No Disponible";
        	} else {
        		rowData[headers[j]] = tableRow.cells[j].innerHTML;
        	}
        }

        data.push(rowData);
    }       

    return data;
}

/*
 * Función que exporta en PDF el historial de un profesor.
 */
function ReportePDF() {
	var nombre = $('#nombre')[0].innerHTML;
	var escolaridad = $('#escolaridad')[0].innerHTML;
	var f = new Date();
	var date = f.getDate() + "-" + (f.getMonth() +1) + "-" + f.getFullYear()
	var archivo = "Historial_" + nombre + "_" + date + ".pdf";
	var pdf = new jsPDF('p', 'pt', 'A4');
	
	var margins = {
		//Posición de filas
		first_row: 180,
		second_row: 210,
		third_row: 240,
		fourth_row: 270,
		fifth_row: 310,
		sixth_row: 340,
		//Posición de columnas
		first_column: 20,
		second_column: 120,
		third_column: 350,
		fourth_column: 450,
		//
		width: 520
	};
	
	var tam = {cell_height: 20};
	
	pdf.setFont("sans-serif");
	pdf.setFontType("bold");
	pdf.setFontSize(16);
	pdf.setTextColor("#000");
	pdf.text(margins.first_column, 60, "SICECD\n\nHistorial de " + nombre + " (" + escolaridad + ")");
	
	pdf.setFontSize(11);
	pdf.setTextColor("#17202A");
	
	/* Start Apartado de CURP */
	pdf.setFontType("bold");
	pdf.text(margins.first_column, margins.first_row, "CURP: ");
	pdf.setFontType("normal");
	pdf.text(margins.second_column,margins.first_row, $('#curp_info')[0].innerHTML);
	/* End Apartado de CURP */
	
	/* Start Apartado de RFC */
	pdf.setFontType("bold");
	pdf.text(margins.third_column, margins.first_row, "RFC: ");
	pdf.setFontType("normal");
	pdf.text(margins.fourth_column, margins.first_row, $('#rfc_info')[0].innerHTML);
	/* End Apartado de RFC */
	
	/* Start Apartado de Género */
	pdf.setFontType("bold");
	pdf.text(margins.first_column, margins.second_row, "Género: ");
	pdf.setFontType("normal");
	pdf.text(margins.second_column,margins.second_row, $('#genero_info')[0].innerHTML);
	/* End Apartado de Género */
	
	/* Start Apartado de Fecha Nacimiento */
	pdf.setFontType("bold");
	pdf.text(margins.third_column, margins.second_row, "Fecha Nac.: ");
	pdf.setFontType("normal");
	pdf.text(margins.fourth_column, margins.second_row, $('#fecha_nac_info')[0].innerHTML);
	/* End Apartado de Fecha Nacimiento */
	
	/* Start Apartado de Correo */
	pdf.setFontType("bold");
	pdf.text(margins.first_column, margins.third_row, "Correo: ");
	pdf.setFontType("normal");
	pdf.text(margins.second_column, margins.third_row, $('#correo_info')[0].innerHTML);	
	/* End Apartado de Correo */
	
	/* Start Apartado de Teléfono */
	pdf.setFontType("bold");
	pdf.text(margins.third_column, margins.third_row, "Teléfono: ");
	pdf.setFontType("normal");
	pdf.text(margins.fourth_column, margins.third_row, $('#telefono_info')[0].innerHTML);
	/* End Apartado de Teléfono */
	
	/* Start Apartado de Localidad */
	pdf.setFontType("bold");
	pdf.text(margins.first_column, margins.fourth_row, "Localidad: ");
	pdf.setFontType("normal");
	pdf.text(margins.second_column, margins.fourth_row, $('#localidad_info')[0].innerHTML);
	/* End Apartado de Localidad */
	
	/* Start Apartado de Plantel */
	pdf.setFontType("bold");
	pdf.text(margins.first_column, margins.fifth_row, "Plantel (Clave): ");
	pdf.setFontType("normal");
	var plantel = $('#plantel_info')[0].innerHTML + "(" + $('#clave_plantel_info')[0].innerHTML + ")"
	pdf.text(margins.second_column,margins.fifth_row, $('#plantel_info')[0].innerHTML);
	/* End Apartado de Plantel */
	
	/* Start Apartado de Turno */
	pdf.setFontType("bold");
	pdf.text(margins.third_column, margins.fifth_row, "Turno: ");
	pdf.setFontType("normal");
	pdf.text(margins.fourth_column, margins.fifth_row, $('#turno_info')[0].innerHTML);
	/* End Apartado de Turno */
	
	/* Start Apartado de Inscripciones */
	var historial = tableToJs($('#tabla_inscripciones').get(0));
	$.each(historial, function(i, row){
		$.each(row, function(j,cell){
			
			if (i==0 && j==0) {
				pdf.cell(margins.first_column, margins.sixth_row, 50, tam.cell_height, "Rol", i);	
			}else if (i==0 && j==1){
				pdf.cell(margins.first_column, margins.sixth_row, 80, tam.cell_height, "Tipo de Curso", i);
			}else if (i==0 && j==2) {
				pdf.cell(margins.first_column, margins.sixth_row, 120, tam.cell_height, "Curso", i);
			}else if (i==0 && j==3 ) {
				pdf.cell(margins.first_column, margins.sixth_row, 40, tam.cell_height, "Grupo", i);
			}else if (i==0 && j==4){
				pdf.cell(margins.first_column, margins.sixth_row, 90, tam.cell_height, "Fecha de Inicio", i);
			}else if (i==0 && j==5){
				pdf.cell(margins.first_column, margins.sixth_row, 100, tam.cell_height, "Fecha de Término", i);
			}else if (i==0 && j==6) {
				pdf.cell(margins.first_column, margins.sixth_row, 80, tam.cell_height, "Calificación", i);
			}
			
			if (j=="rol") {
				pdf.cell(margins.first_column, margins.sixth_row, 50, tam.cell_height, cell, i);
			}else if (j=="tipodecurso"){
				pdf.cell(margins.first_column, margins.sixth_row, 80, tam.cell_height, cell, i);
			}else if (j=="curso") {
				pdf.cell(margins.first_column, margins.sixth_row, 120, tam.cell_height, cell, i);
			}else if (j=="grupo") {
				pdf.cell(margins.first_column, margins.sixth_row, 40, tam.cell_height, cell, i);
			}else if (j=="fechadeinicio"){
				pdf.cell(margins.first_column, margins.sixth_row, 90, tam.cell_height, cell, i);
			}else if (j=="fechadetérmino"){
				pdf.cell(margins.first_column, margins.sixth_row, 100, tam.cell_height, cell, i);
			}else if (j=="calificación") {
				pdf.cell(margins.first_column, margins.sixth_row, 80, tam.cell_height, cell, i);
			}
		});
	});
	/* End Apartado de Inscripciones */
	
	pdf.save(archivo);
}