package co.com.compraya.admin.usuario;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.com.compraya.admin.FileUploadUtil;
import co.com.compraya.common.entity.Role;
import co.com.compraya.common.entity.Usuario;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ControladorUsuario {

	@Autowired
	private ServicioUsuario servicio;

	@GetMapping("/usuarios")
	public String listFirstPage(Model model) {
		return listByPage (1, model, "primerNombre", "asc", null);
	}
	
	@GetMapping("/usuarios/pagina/{numeroPagina}")
	public String listByPage(@PathVariable(name = "numeroPagina") int numeroPagina, Model model,
			@Param("campoSort") String campoSort, @Param("direccionSort") String direccionSort,
			@Param("textoBusqueda") String textoBusqueda
			) {
		
		Page<Usuario> pagina = servicio.listByPage(numeroPagina, campoSort, direccionSort, textoBusqueda);
		List<Usuario> listaUsuarios = pagina.getContent();
		
		long inicioContador = (numeroPagina - 1) * ServicioUsuario.USUARIOS_POR_PAGINA + 1;
		long finContador = inicioContador + ServicioUsuario.USUARIOS_POR_PAGINA - 1;
		if (finContador > pagina.getTotalElements()) {
			finContador = pagina.getTotalElements();
		}
		
		String direccionSortInversa = direccionSort.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("paginaActual", numeroPagina);
		model.addAttribute("paginasTotales", pagina.getTotalPages());
		model.addAttribute("inicioContador", inicioContador);
		model.addAttribute("finContador", finContador);
		model.addAttribute("totalItems", pagina.getTotalElements());
		model.addAttribute("listaUsuarios", listaUsuarios);
		model.addAttribute("campoSort", campoSort);
		model.addAttribute("direccionSort", direccionSort);
		model.addAttribute("direccionSortInversa", direccionSortInversa);
		model.addAttribute("textoBusqueda", textoBusqueda);
		
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
	public String guardarUsuario (Usuario usuario, RedirectAttributes redirectAttributes,
			@RequestParam("imagen") MultipartFile multipartFile) throws IOException {
		
		if(!multipartFile.isEmpty()) {
			
			String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			usuario.setFotos(nombreArchivo);
			Usuario usuarioGuardado = servicio.guardar(usuario);
			
			String directorioCargue = "foto-usuarios/" + usuarioGuardado.getId();
			
			FileUploadUtil.limpiarDirectorio(directorioCargue);
			FileUploadUtil.guardarArchivo(directorioCargue, nombreArchivo, multipartFile);
			
		} else {
			if(usuario.getFotos().isEmpty()) usuario.setFotos(null);
			servicio.guardar(usuario);
		}
		
		redirectAttributes.addFlashAttribute("message", "El usuario fue guardado correctamente");
		
		return obtenerURLparaRedireccionarAlUsuarioModificado(usuario);
	}

	private String obtenerURLparaRedireccionarAlUsuarioModificado(Usuario usuario) {
		String primeraParteStringDeEmail = usuario.getEmail().split("@")[0];
		return "redirect:/usuarios/pagina/1?campoSort=id&direccionSort=asc&textoBusqueda=" + primeraParteStringDeEmail;
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
	
	@GetMapping("/usuarios/{id}/estadousuario/{estado}")
	public String updateEstadoUsuario(@PathVariable("id") Integer id, 
		@PathVariable("estado") boolean activo, RedirectAttributes redirectAttributes) {
		servicio.updateEstadoUsuario (id, activo);
		String estadoUsuario = activo ? "activado" : "desactivado";
		String mensaje = "El Usuario con ID: " + id + " ha sido " + estadoUsuario;
		redirectAttributes.addFlashAttribute("message", mensaje);
		
		return "redirect:/usuarios";
	}
	
	@GetMapping("/usuarios/exportar/csv")
	public void exportarCSV(HttpServletResponse respuesta) throws IOException{
		List<Usuario> listaUsuarios = servicio.listAll();
		UsuarioCsvExporter exportador = new UsuarioCsvExporter();
		exportador.export(listaUsuarios, respuesta);
		
	}
	
}
