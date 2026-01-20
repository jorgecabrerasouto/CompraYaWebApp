package co.com.compraya.admin.marca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorRestMarca {
	@Autowired
	private ServicioMarca servicioMarca;
	
	@PostMapping("/marcas/chequear_unico")
	public String chequearUnico(@Param("id") Integer id, @Param("nombre") String nombre) {
		return servicioMarca.chequearUnico(id, nombre);
	}

}
