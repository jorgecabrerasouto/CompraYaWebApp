package co.com.compraya.admin.usuario;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.com.compraya.admin.FileUploadUtil;
import co.com.compraya.admin.security.CompraYaUserDetails;
import co.com.compraya.common.entity.User;

@Controller
public class ControladorCuenta {

	@Autowired
	private ServicioUsuario servicio;
	
	@GetMapping("/cuenta")
	public String verDetalleString (@AuthenticationPrincipal CompraYaUserDetails usuarioAutenticado,
			Model model) {
		String email = usuarioAutenticado.getUsername();
		User user = servicio.getByEmail(email);
		model.addAttribute("user", user);
		
		return "forma_cuenta";
	}
	
	@PostMapping("/cuenta/actualizar")
	public String guardarDetalles (User usuario, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal CompraYaUserDetails usuarioActual,
			@RequestParam("imagen") MultipartFile multipartFile) throws IOException {
		
		if(!multipartFile.isEmpty()) {
			
			String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			usuario.setFotos(nombreArchivo);
			User usuarioGuardado = servicio.actualizarCuenta(usuario);
			
			String directorioCargue = "foto-usuarios/" + usuarioGuardado.getId();
			
			FileUploadUtil.limpiarDirectorio(directorioCargue);
			FileUploadUtil.guardarArchivo(directorioCargue, nombreArchivo, multipartFile);
			
		} else {
			if(usuario.getFotos().isEmpty()) usuario.setFotos(null);
			servicio.actualizarCuenta(usuario);
		}
		
		usuarioActual.setPrimerNombre(usuario.getPrimerNombre());
		usuarioActual.setPrimerApellido(usuario.getPrimerApellido());
		
		redirectAttributes.addFlashAttribute("message", "Los detalles de su cuenta han sido actualizados");
		
		return "redirect:/cuenta";
	}
}
