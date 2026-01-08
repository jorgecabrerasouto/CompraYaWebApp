function clearFilter() {
	window.location = moduleURL;
}

function mostrarConfirmarEliminacionModal(link, nombreEntidad) {
	idEntidad = link.attr("idEntidad");
	
	$("#yesButton").attr("href", link.attr("href"));
	$("#confirmText").text("¿Esta seguro de que quiere eliminar este(a) "
								+ nombreEntidad + " (ID " + idEntidad + ")?");
	$("#confirmModal").modal();
}