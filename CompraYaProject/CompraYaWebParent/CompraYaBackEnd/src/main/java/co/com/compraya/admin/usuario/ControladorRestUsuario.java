package co.com.compraya.admin.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControladorRestUsuario {

	@Autowired
	private ServicioUsuario servicio;
	
	@PostMapping("/usuarios/check_email")
	public String checkEmailDuplicado(@Param("email") String email) {
		return servicio.esEmailUnico(email) ? "OK" : "Duplicado";
	}
}
