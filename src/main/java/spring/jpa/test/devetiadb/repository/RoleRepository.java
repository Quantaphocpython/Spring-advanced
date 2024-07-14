package spring.jpa.test.devetiadb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.jpa.test.devetiadb.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
