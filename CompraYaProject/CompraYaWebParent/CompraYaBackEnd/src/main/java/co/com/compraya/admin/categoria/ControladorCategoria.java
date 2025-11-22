package co.com.compraya.admin.categoria;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import co.com.compraya.common.entity.Categoria;

@Controller
public class ControladorCategoria {
	
	@Autowired
	private ServicioCategoria servicio;
	
	@GetMapping("/categorias")
	public String listAll(@Param("sortDir") String sortDir, Model model) {
		if (sortDir == null || sortDir.isEmpty()) {
			sortDir = "asc";
		}
		
		List<Categoria> listaCategorias = servicio.listAll(sortDir);
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("listaCategorias", listaCategorias);
		model.addAttribute("reverseSortDir", reverseSortDir);
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
		if(!multipartFile.isEmpty()) {
			String nombreArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			categoria.setImagen(nombreArchivo);
		
			Categoria categoriaGuardada = servicio.save(categoria);
			String directorioCargue = "../imagenes-categorias/" + categoriaGuardada.getId();
			
			FileUploadUtil.limpiarDirectorio(directorioCargue);
			FileUploadUtil.guardarArchivo(directorioCargue, nombreArchivo, multipartFile);
		} else {
			servicio.save(categoria);
		}
		
		ra.addFlashAttribute("message", "La categoría fue guardada correctamente.");
		return "redirect:/categorias";
	}
	
	@GetMapping("/categorias/editar/{id}")
	public String editarCategoria(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Categoria categoria = servicio.get(id);
			List<Categoria> listaCategorias = servicio.listaCategoriasUsadaEnForma();
			
			model.addAttribute("categoria", categoria);
			model.addAttribute("listaCategorias", listaCategorias);
			model.addAttribute("tituloPagina", "Editar Categoria (ID: " + id + ")");
			
			return "categorias/forma_categoria";		
		} catch (CategoriaNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/categorias";	
		}
	}

	@GetMapping("/categorias/{id}/estadocategoria/{estado}")
	public String actualizarEstadoCategoria(@PathVariable("id") Integer id, 
			@PathVariable("estado") boolean activa, RedirectAttributes redirectAttributes) {
		servicio.updateEstadoCategoria(id, activa);
		String estado = activa ? "activada" : "desactivada";
		String mensaje = "La categoria con ID " + id + " ha sido " + estado;
		redirectAttributes.addFlashAttribute("message", mensaje);
		
			return "redirect:/categorias";
	}
}
