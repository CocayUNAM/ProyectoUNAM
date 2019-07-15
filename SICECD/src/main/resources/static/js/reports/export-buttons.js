/*function ReportePDF() {
	
	var pdf = new jsPDF({orientation: 'l'});
	var res = pdf.autoTableHtmlToJson(document.getElementById('bootstrap-data-table-export'));
	
	pdf.autoTable(
			res.columns,
			res.data,
			{margin: {top: 65},
				styles: {overflow: 'linebreak'},
				columnStyles: config,
				showHeader: 'everyPage',
				addPageContent: pageContent}
	);
		
	pdf.text('Footer Text', data.settings.margin.left, pdf.internal.pageSize.height - 10);
	pdf.text(watermark, data.settings.margin.right * 13, pdf.internal.pageSize.height - 10);
}*/

function ReportePDF() {
	//var pdf = new jsPDF();
	//pdf.fromHTML($('#ins_profesor').get(0), 15, 15, { 'width': 170 });
	
	var f = new Date();
	var date = f.getDate() + "-" + (f.getMonth() +1) + "-" + f.getFullYear()
	var archivo = "Historial_" + date + ".pdf";
	
	var pdf = new jsPDF('p', 'pt', 'A4');
	
	specialElementHandlers = {
		'#bypassme': function (element, renderer) {
			return true
			}
	};
	
	margins = {
		//Posición de filas
		first_row: 90,
		second_row: 120,
		third_row: 150,
		fourth_row: 180,
		//Posición de columnas
		first_column: 40,
		second_column: 120,
		third_column: 350,
		fourth_column: 430,
		bottom: 60,
		left: 40,
		width: 520
	};
	
	pdf.fromHTML(
		$('#nombre')[0], 
		margins.left,
		margins.first_column, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	
	/* Start Apartado de CURP */
	pdf.fromHTML(
		$('#curp_label')[0], 
		margins.first_column,
		margins.first_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	
	pdf.fromHTML(
		$('#curp_info')[0], 
		margins.second_column,
		margins.first_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	/* End Apartado de CURP */
	
	/* Start Apartado de RFC */
	pdf.fromHTML(
		$('#rfc_label')[0], 
		margins.third_column,
		margins.first_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	
	pdf.fromHTML(
		$('#rfc_info')[0], 
		margins.fourth_column,
		margins.first_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	/* End Apartado de RFC */
	
	/* Start Apartado de Grado de Estudios */
	pdf.fromHTML(
		$('#escolaridad_label')[0], 
		margins.first_column,
		margins.second_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	
	pdf.fromHTML(
		$('#escolaridad_info')[0], 
		margins.second_column,
		margins.second_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	/* End Apartado de Grado de Estudios */
	
	/* Start Apartado de Turno */
	pdf.fromHTML(
		$('#turno_label')[0], 
		margins.third_column,
		margins.second_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	
	pdf.fromHTML(
		$('#turno_info')[0], 
		margins.fourth_column,
		margins.second_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	/* End Apartado de Turno */
	
	/* Start Apartado de Correo */
	pdf.fromHTML(
		$('#correo_label')[0], 
		margins.first_column,
		margins.third_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	
	pdf.fromHTML(
		$('#correo_info')[0], 
		margins.second_column,
		margins.third_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	/* End Apartado de Correo */
	
	/* Start Apartado de Teléfono */
	pdf.fromHTML(
		$('#telefono_label')[0], 
		margins.third_column,
		margins.third_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	
	pdf.fromHTML(
		$('#telefono_info')[0], 
		margins.fourth_column,
		margins.third_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	/* End Apartado de Teléfono */
	
	/* Start Apartado de Localidad */
	pdf.fromHTML(
		$('#localidad_label')[0], 
		margins.first_column,
		margins.fourth_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	
	pdf.fromHTML(
		$('#localidad_info')[0], 
		margins.second_column,
		margins.fourth_row, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	/* End Apartado de Localidad */
	
	/* Start Apartado de Inscripciones */
	pdf.fromHTML(
		$('#tabla_inscripciones')[0], 
		margins.left,
		200, {
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		margins
	);
	/* End Apartado de Inscripciones */
	
	pdf.save(archivo);
}