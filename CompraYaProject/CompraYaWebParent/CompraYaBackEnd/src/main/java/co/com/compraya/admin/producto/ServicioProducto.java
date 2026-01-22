package co.com.compraya.admin.producto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.compraya.common.entity.Producto;

@Service
public class ServicioProducto {

	@Autowired 
	private ProductoRepository repo;
	
	public List<Producto> listAll() {
		return (List<Producto>) repo.findAll();
	}
}