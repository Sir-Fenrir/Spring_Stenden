package stenden.spring.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import stenden.spring.data.model.AnnotatedHouse;

public interface SpringJpaRepository extends JpaRepository<AnnotatedHouse, Long> {
}
