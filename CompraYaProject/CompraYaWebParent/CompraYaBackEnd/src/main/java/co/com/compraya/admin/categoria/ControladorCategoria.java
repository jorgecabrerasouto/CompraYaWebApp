package co.com.compraya.admin.categoria;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.com.compraya.admin.FileUploadUtil;
import co.com.compraya.common.entity.Categoria;

@Controller
public class ControladorCategoria {
	
	@Autowired
	private ServicioCategoria servicio;
	
	@GetMapping("/categorias")
	public String listAll(Model model) {
		List<Categoria> listaCategorias = servicio.listAll();
		model.addAttribute("listaCategorias", listaCategorias);
		
		return "categorias/categorias";
	}
	
	@GetMapping("/categorias/nueva")
	public String CategoriaNueva(Model model) {
		List<Categoria> listaCategorias = servicio.listaCategoriasUsadaEnForma();
		
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("listaCategorias", listaCategorias);
		model.addAttribute("pageTitle", "Crear una Categoría nueva");
		
		return "categorias/forma_categoria";
	}

	@PostMapping("/categorias/guardar")
	public String guardarCategoria(Categoria categoria, 
			@RequestParam("archivoImagen") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		categoria.setImagen(nombreArchivo);
		
		Categoria categoriaGuardada = servicio.save(categoria);
		String uploadDir ="../imagenes-categorias/" + categoriaGuardada.getId();
		FileUploadUtil.guardarArchivo(uploadDir, nombreArchivo, multipartFile);
		
		ra.addFlashAttribute("message", "La categoría fue guardada correctamente.");
		return "redirect:/categorias";
	}
}
