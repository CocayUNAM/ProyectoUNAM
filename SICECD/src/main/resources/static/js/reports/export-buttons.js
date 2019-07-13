function ReportePDF() {
	
	/*var pdf = new jsPDF({orientation: 'l'});
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
	pdf.text(watermark, data.settings.margin.right * 13, pdf.internal.pageSize.height - 10);*/
}

function ReportePDF() {
	var pdf = new jsPDF('p', 'pt', 'A4');
	source = $('#ins_profesor')[0];
	var rfc = $('#rfc')[0];
	var escolaridad = $('#escolaridad')[0];
	var turno = $('#turno')[0];
	console.log(rfc);
	console.log(escolaridad);
	console.log(turno);
	
	var f = new Date();
	var date = f.getDate() + "-" + (f.getMonth() +1) + "-" + f.getFullYear()
	var archivo = "Historial_" + date + ".pdf";
	
	specialElementHandlers = {
		'#bypassme': function (element, renderer) {
			return true
			}
	};
	
	margins = {
		top: 80,
		bottom: 60,
		left: 40,
		width: 522
	};
	
	pdf.fromHTML(
		source, 
		margins.left, // x coord
		margins.top, { // y coord
			'width': margins.width, 
			'elementHandlers': specialElementHandlers
		},
		
		function (dispose) {
			pdf.save(archivo);
		}, margins
	);
}