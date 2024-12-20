package co.com.compraya.admin.usuario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.com.compraya.common.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

}
