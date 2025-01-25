package co.com.compraya.admin.usuario.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.compraya.admin.usuario.ServicioUsuario;

@RestController
public class ControladorRestUsuario {

	@Autowired
	private ServicioUsuario servicio;
	
	@PostMapping("/usuarios/check_email")
	public String checkEmailDuplicado(@Param("id") Integer id, @Param("email") String email) {
		return servicio.esEmailUnico(id, email) ? "OK" : "Duplicado";
	}
}
