package co.com.compraya.admin.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.compraya.common.entity.Usuario;

@Service
public class ServicioUsuario {
	
	@Autowired
	private UsuarioRepository repo;
	
	public List<Usuario> listAll() {
		return (List<Usuario>) repo.findAll();
	}

}
