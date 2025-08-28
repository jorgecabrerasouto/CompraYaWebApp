package co.com.compraya.admin.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorRestCategoria {
	
	@Autowired
	private ServicioCategoria servicio;
	
	@PostMapping("/categorias/chequear_unico")
	public String chequearUnico(@Param("id") Integer id, @Param("nombre") String nombre,
			@Param("alias") String alias) {
		return servicio.chequearUnico(id, nombre, alias);
	}
}
