package stenden.spring.data.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import stenden.spring.data.House;
import stenden.spring.data.HouseRepository;

import javax.transaction.Transactional;

@Repository
public class HibernateRepository implements HouseRepository {

  private SessionFactory sessionFactory;

  @Autowired
  public HibernateRepository(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  private Session currentSession() {
    return sessionFactory.getCurrentSession();
  }

  @Transactional
  public House getByID(Long id) {
    return currentSession().get(HibernateHouse.class, id);
  }

}
