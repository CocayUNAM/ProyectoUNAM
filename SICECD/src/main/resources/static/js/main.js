$(document).on({
	ajaxStart: function(){
		$("body").addClass("loading");
	},
	ajaxStop: function(){
		$("body").removeClass("loading");
		console.log("si se ejecuta");
	}
});