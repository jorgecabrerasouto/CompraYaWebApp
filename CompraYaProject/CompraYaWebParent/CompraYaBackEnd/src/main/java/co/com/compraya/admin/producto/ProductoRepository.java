package co.com.compraya.admin.producto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import co.com.compraya.common.entity.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Integer>, PagingAndSortingRepository<Producto, Integer> {

}
