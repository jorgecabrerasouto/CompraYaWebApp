package co.com.compraya.admin.usuario;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.compraya.common.entity.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	@Query ("SELECT u FROM Usuario u WHERE u.email = :email")
	public Usuario getUsuarioByEmail(@Param("email") String email);
}
