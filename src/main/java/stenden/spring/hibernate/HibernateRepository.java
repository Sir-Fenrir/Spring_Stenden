package stenden.spring.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class HibernateRepository {

  private SessionFactory sessionFactory;

  @Autowired
  public HibernateRepository(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  private Session currentSession() {
    return sessionFactory.getCurrentSession();
  }

  @Transactional
  public HibernateHouse getByID(Long id) {
    return currentSession().get(HibernateHouse.class, id);
  }

}
