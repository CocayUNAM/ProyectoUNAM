function pruebaDivAPdf() {
	var pdf = new jsPDF('p', 'pt', 'A4');
	var nombre = document.getElementById("#nombre").val();
	console.log(nombre);
	pdf.setFontSize(16);
	pdf.text(nombre, 20, 20);
	source = $('#ins_profesor')[0];
	console.log(source);
	
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
			pdf.save('Prueba.pdf');
		}, margins
	);
}