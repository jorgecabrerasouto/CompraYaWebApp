package co.com.compraya.admin.producto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import co.com.compraya.common.entity.Producto;

@Controller
public class ControladorProducto {
	@Autowired 
	private ServicioProducto servicio;

	@GetMapping("/productos")
	public String listAll(Model model) {
		List<Producto> listaProductos = servicio.listAll();
		
		model.addAttribute("listaProductos", listaProductos);
		
		return "productos/productos";
	}
}
