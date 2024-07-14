package spring.jpa.test.devetiadb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.jpa.test.devetiadb.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByName(String name);

    Optional<User> findByName(String name);
}
