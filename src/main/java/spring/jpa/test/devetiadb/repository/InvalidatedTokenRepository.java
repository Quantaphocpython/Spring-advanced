package spring.jpa.test.devetiadb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedTokenRepository, String> {

}
