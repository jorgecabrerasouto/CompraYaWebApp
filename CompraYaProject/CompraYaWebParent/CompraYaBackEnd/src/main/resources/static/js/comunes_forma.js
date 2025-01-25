$(document).ready(function() {
	$("#botonCancelar").on("click", function() {
		window.location = URLModulo;
	});

	$("#archivoImagen").change(function() {				
		tamanoMaximo = (1024 ** 2);
		tamanoArchivo = this.files[0].size;
	
		if(tamanoArchivo >= tamanoMaximo) {
			this.setCustomValidity("Debe escoger una imagen con tamaño menor a 1 MB!");
			this.reportValidity();
		} else {
			this.setCustomValidity("");
			showImageThumbnail(this);
		}
	});
});

function showImageThumbnail(archivoEntrada) {
	var archivo = archivoEntrada.files[0];
	var reader = new FileReader();
	reader.onload = function (e) {
		$("#thumbnail").attr("src", e.target.result);
	};	
	
	reader.readAsDataURL(archivo);
}