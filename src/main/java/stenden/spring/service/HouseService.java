package stenden.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stenden.spring.data.House;
import stenden.spring.data.HouseRepository;
import stenden.spring.data.jpa.SpringJpaRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class HouseService {

  private final HouseRepository jdbcTemplateRepository;
  private final HouseRepository pureJdbcRepository;
  private final HouseRepository hibernateRepository;
  private final HouseRepository entityManagerJpaRepository;
  private final SpringJpaRepository springJpaRepository;

  @Autowired
  public HouseService(HouseRepository jdbcTemplateRepository,
                      HouseRepository pureJdbcRepository,
                      HouseRepository hibernateRepository,
                      HouseRepository entityManagerJpaRepository,
                      SpringJpaRepository springJpaRepository) {
    this.jdbcTemplateRepository = jdbcTemplateRepository;
    this.pureJdbcRepository = pureJdbcRepository;
    this.hibernateRepository = hibernateRepository;
    this.entityManagerJpaRepository = entityManagerJpaRepository;
    this.springJpaRepository = springJpaRepository;
  }

  public House getTemplateJdbcHouse(Long id) {
    return jdbcTemplateRepository.getByID(id);
  }

  public House getPureJdbcHouse(Long id) {
    return pureJdbcRepository.getByID(id);
  }

  public House getHibernateHouse(Long id) {
    return hibernateRepository.getByID(id);
  }

  public House getJpaHouse(Long id) {
    return entityManagerJpaRepository.getByID(id);
  }

  public House getSpringJpaHouse(Long id) {
    return springJpaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("House with ID " + id + " not found"));
  }

}

