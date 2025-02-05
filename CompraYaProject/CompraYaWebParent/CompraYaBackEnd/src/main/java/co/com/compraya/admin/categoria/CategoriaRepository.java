package co.com.compraya.admin.categoria;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import co.com.compraya.common.entity.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Integer>, PagingAndSortingRepository<Categoria, Integer> {

}
