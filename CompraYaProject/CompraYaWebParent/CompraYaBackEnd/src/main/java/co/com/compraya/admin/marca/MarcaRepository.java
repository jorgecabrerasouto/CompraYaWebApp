package co.com.compraya.admin.marca;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import co.com.compraya.common.entity.Marca;

public interface MarcaRepository extends CrudRepository<Marca, Integer>, PagingAndSortingRepository<Marca, Integer> {
	
	public long countById (Integer id);

}
