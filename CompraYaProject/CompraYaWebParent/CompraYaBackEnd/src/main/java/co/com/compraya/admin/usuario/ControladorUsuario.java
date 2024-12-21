package co.com.compraya.admin.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.com.compraya.common.entity.Role;
import co.com.compraya.common.entity.Usuario;

@Controller
public class ControladorUsuario {

	@Autowired
	private ServicioUsuario servicio;

	@GetMapping("/usuarios")
	public String listAll(Model model) {
		List<Usuario> listaUsuarios = servicio.listAll();
		model.addAttribute("listaUsuarios", listaUsuarios);
		return "usuarios";
	}
	
	@GetMapping("/usuarios/nuevo")
	
	public String nuevoUsuario(Model model) {
		List<Role> listaRoles = servicio.listaRoles();
		
		Usuario usuario = new Usuario();
		usuario.setActivo(true);
		model.addAttribute("usuario", usuario);
		model.addAttribute("listaRoles", listaRoles);
		
		return "forma_usuario";
	}
	
	@PostMapping("/usuarios/guardar")
	public String guardarUsuario (Usuario usuario, RedirectAttributes redirectAttributes) {
		System.out.println(usuario);
		servicio.guardar(usuario);
		
		redirectAttributes.addFlashAttribute("message", "El usuario fue adicionado correctamente");
		
		return "redirect:/usuarios";
	}
	
}
