package co.com.compraya.admin.marca;

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
import co.com.compraya.admin.categoria.ServicioCategoria;
import co.com.compraya.common.entity.Categoria;
import co.com.compraya.common.entity.Marca;

@Controller
public class ControladorMarca {

	@Autowired
	private ServicioMarca servicioMarca;
	
	@Autowired
	private ServicioCategoria servicioCategoria;
	
	@GetMapping("/marcas")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "nombre", "asc", null);
	}
	
	@GetMapping("/marcas/pagina/{numPagina}")
	public String listByPage(
			@PathVariable int numPagina, Model model,
			@Param("campoSort") String campoSort, @Param("direccionSort") String direccionSort,
			@Param("textoBusqueda") String textoBusqueda
			) {
		Page<Marca> page = servicioMarca.listByPage(numPagina, campoSort, direccionSort, textoBusqueda);
		List<Marca> listaMarcas = page.getContent();
		
		long inicioContador = (numPagina - 1) * ServicioMarca.MARCAS_POR_PAGINA + 1;
		long finContador = inicioContador + ServicioMarca.MARCAS_POR_PAGINA - 1;
		if (finContador > page.getTotalElements()) {
			finContador = page.getTotalElements();
		}
		
		String direccionSortInversa = direccionSort.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("paginaActual", numPagina);
		model.addAttribute("paginasTotales", page.getTotalPages());
		model.addAttribute("inicioContador", inicioContador);
		model.addAttribute("finContador", finContador);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("campoSort", campoSort);
		model.addAttribute("direccionSort", direccionSort);
		model.addAttribute("direccionSortInversa", direccionSortInversa);
		model.addAttribute("textoBusqueda", textoBusqueda);		
		model.addAttribute("listaMarcas", listaMarcas);
		
		return "marcas/marcas";		
	}
	
	
	@GetMapping("/marcas/nueva")
	public String nuevaMarca(Model model) {
		List<Categoria> listaCategorias = servicioCategoria.listaCategoriasUsadasEnForma();
		
		model.addAttribute("listaCategorias", listaCategorias);
		model.addAttribute("marca", new Marca());
		model.addAttribute("tituloPagina", "Cear una nueva Marca");
		
		return "marcas/forma_marca";
	}
	
	@PostMapping("/marcas/guardar")
	public String guardarMarca(Marca marca, @RequestParam("archivoImagen") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) throws IOException {
		if(!multipartFile.isEmpty()) {
			String trayectoriaArchivo = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			marca.setLogo(trayectoriaArchivo);
			
			Marca marcaGuardada = servicioMarca.save(marca);
			String uploadDir = "../logos-marca/" + marcaGuardada.getId();
			
			FileUploadUtil.limpiarDirectorio(uploadDir);
			FileUploadUtil.guardarArchivo(uploadDir, trayectoriaArchivo, multipartFile);
			
		} else {
			servicioMarca.save(marca);
		}
		redirectAttributes.addFlashAttribute("message", "La marca fue guardada exitosamente.");
		return "redirect:/marcas";
	}
	
	@GetMapping("/marcas/editar/{id}")
	public String editarMarca(@PathVariable Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Marca marca = servicioMarca.get(id);
			List<Categoria> listaCategories = servicioCategoria.listaCategoriasUsadasEnForma();
			
			model.addAttribute("marca", marca);
			model.addAttribute("listaCategorias", listaCategories);
			model.addAttribute("tituloPagina", "Editar marca (ID: " + id + ")");
			
			return "marcas/forma_marca";			
		} catch (MarcaNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/marcas";
		}
	}
	
	@GetMapping("/marcas/eliminar/{id}")
	public String eliminarMarca(@PathVariable Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			servicioMarca.eliminar(id);
			String dirMarca = "../logos-marca/" + id;
			FileUploadUtil.eliminarDir(dirMarca);
			
			redirectAttributes.addFlashAttribute("message", 
					"La Marca con ID " + id + " ha sido eliminada exitosamente");
		} catch (MarcaNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/marcas";
	}	
		
}
