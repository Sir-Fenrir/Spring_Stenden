package stenden.spring.data.jpa;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import stenden.spring.data.House;
import stenden.spring.data.HouseRepository;
import stenden.spring.data.model.AnnotatedHouse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@AllArgsConstructor
@NoArgsConstructor
public class EntityManagerJpaRepository implements HouseRepository {

  /**
   * This gives us an EntityManager.
   * Or, well, a proxy to one. Which gives or creates a thread-safe EntityManager for us
   * every time we use it.
   */
  @PersistenceContext(unitName = "entityManagerFactory")
  private EntityManager em;

  @Override
  @Transactional
  public House getByID(Long id) {
    return em.find(AnnotatedHouse.class, id);
  }
}
