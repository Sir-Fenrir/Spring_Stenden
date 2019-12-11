package stenden.spring.data.jpa;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import stenden.spring.data.House;
import stenden.spring.data.HouseRepository;
import stenden.spring.data.model.AnnotatedHouse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

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
    protected EntityManager em;

    @Override
    @Transactional
    public House getByID(Long id) {
        TypedQuery<AnnotatedHouse> getAll = em.createQuery("SELECT e FROM AnnotatedHouse e", AnnotatedHouse.class);
        List<AnnotatedHouse> resultList = getAll.getResultList();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<AnnotatedHouse> query = criteriaBuilder.createQuery(AnnotatedHouse.class);
        Root<AnnotatedHouse> from = query.from(AnnotatedHouse.class);
        CriteriaQuery<AnnotatedHouse> select = query.select(from);
        select.where(criteriaBuilder.equal(from.get("ID"), 1));
        TypedQuery<AnnotatedHouse> query1 = em.createQuery(select);
        List<AnnotatedHouse> resultList1 = query1.getResultList();


        return em.find(AnnotatedHouse.class, id);
    }

    @Override
    @Transactional
    public House addHouse(House house) {
        em.persist(house);
        return house;
    }
}
