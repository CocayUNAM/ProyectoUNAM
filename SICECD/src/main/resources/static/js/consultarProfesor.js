$(document).ready(function(){
	llenarTabla();
});

function llenarTabla(){
	$('#tablaPrueba').bootstrapTable({
		search: true,
		pagination: true,
		pageSize: 5,
		pageList: [5, 25, 50],
		clickToSelect: false,
		singleSelect: false,
		maintainSelected: true,
		sortable: true,
		checkboxHeader: false,
//		data: [],
		sidePagination: 'server',
		url: "http://localhost:8080/rest/CargaBatchService/lstProfesores",
		queryParams: 'queryParams',
		responseHandler : "responseHandler",	
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

function queryParams(params) {
    return {
        limit: params.limit,
        offset: params.offset,
        search: params.search,
        name: params.sort,
        order: params.order
    };
}

function responseHandler(res) {
	return {
        rows: res.response.rows,
        total: res.response.total
    };
}
