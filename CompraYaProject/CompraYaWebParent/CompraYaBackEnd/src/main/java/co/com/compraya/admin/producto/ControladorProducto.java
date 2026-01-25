package co.com.compraya.admin.producto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import co.com.compraya.admin.marca.ServicioMarca;
import co.com.compraya.common.entity.Marca;
import co.com.compraya.common.entity.Producto;

@Controller
public class ControladorProducto {
	@Autowired 
	private ServicioProducto servicioProducto;
	
	@Autowired 
	private ServicioMarca servicioMarca;	

	@GetMapping("/productos")
	public String listAll(Model model) {
		List<Producto> listaProductos = servicioProducto.listAll();
		
		model.addAttribute("listaProductos", listaProductos);
		
		return "productos/productos";
	}
	
	@GetMapping("/productos/nuevo")
	public String productoNuevo (Model model) {
		List<Marca> listaMarcas = servicioMarca.listAll();
		
		Producto producto = new Producto();
		producto.setActivo(true);
		producto.setEnExistencia(true);
		
		model.addAttribute("producto", producto);
		model.addAttribute("listaMarcas", listaMarcas);
		model.addAttribute("tituloPagina", "Crear un Producto nuevo");
		
		return "productos/forma_producto";
	}
	
	@PostMapping("/productos/guardar")
	public String guardarProducto(Producto producto) {
		System.out.println("Nombre del producto: " + producto.getNombre());
		System.out.println("ID Marca: " + producto.getMarca().getId());
		System.out.println("ID Categoria: " + producto.getCategoria().getId());
		
		return "redirect:/productos";
	}
}
