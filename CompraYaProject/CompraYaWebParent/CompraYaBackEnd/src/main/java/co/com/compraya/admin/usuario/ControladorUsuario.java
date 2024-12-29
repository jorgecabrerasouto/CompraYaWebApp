package co.com.compraya.admin.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		model.addAttribute("tituloPagina", "Cear un nuevo usuario");
		
		return "forma_usuario";
	}
	
	@PostMapping("/usuarios/guardar")
	public String guardarUsuario (Usuario usuario, RedirectAttributes redirectAttributes) {
		servicio.guardar(usuario);
		
		redirectAttributes.addFlashAttribute("message", "El usuario fue guardado correctamente");
		
		return "redirect:/usuarios";
	}
	
	@GetMapping("/usuarios/editar/{id}")
	public String editarUsuario(@PathVariable(name = "id") Integer id,
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Usuario usuario = servicio.get(id);
			List<Role> listaRoles = servicio.listaRoles();
			
			model.addAttribute("usuario", usuario);
			model.addAttribute("listaRoles", listaRoles);
			model.addAttribute("tituloPagina", "Editar el usuario (ID: " + id +")");			
			
			return "forma_usuario";
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/usuarios";
		}
		
	}
	@GetMapping("/usuarios/eliminar/{id}")
	public String eliminarUsuario (@PathVariable(name = "id") Integer id,
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			servicio.eliminar(id);
			redirectAttributes.addFlashAttribute("message", 
					"EL usuario con ID " + id + " ha sido borrado exitosamente");
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());			
		}			
		
		return "redirect:/usuarios";
	}
	
}
