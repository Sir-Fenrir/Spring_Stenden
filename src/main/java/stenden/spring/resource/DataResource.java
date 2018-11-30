package stenden.spring.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stenden.spring.data.House;
import stenden.spring.data.HouseRepository;
import stenden.spring.data.jpa.SpringJpaRepository;

// We're telling Spring MVC that this is a REST controller
// This treats methods with @RequestMapping as if it had the @ResponseBody annotation
// which tells Spring that the return value should be transformed into a response
@RestController
// The base request mapping this controller listens to, we respond to anything starting with /data
@RequestMapping("/data")
public class DataResource {

  private final HouseRepository jdbcTemplateRepository;
  private final HouseRepository pureJdbcRepository;
  private final HouseRepository hibernateRepository;
  private final HouseRepository entityManagerJpaRepository;
  private final SpringJpaRepository springJpaRepository;

  @Autowired
  public DataResource(HouseRepository jdbcTemplateRepository,
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

  @GetMapping("/templatejdbc/{id}")
  public House getTemplateJdbcHouse(@PathVariable("id") Long id) {
    return jdbcTemplateRepository.getByID(id);
  }

  @GetMapping("/purejdbc/{id}")
  public House getPureJdbcHouse(@PathVariable("id") Long id) {
    return pureJdbcRepository.getByID(id);
  }

  @GetMapping("/hibernate/{id}")
  public House getHibernateHouse(@PathVariable("id") Long id) {
    return hibernateRepository.getByID(id);
  }

  @GetMapping("/jpa/{id}")
  public House getJpaHouse(@PathVariable("id") Long id) {
    return entityManagerJpaRepository.getByID(id);
  }

  @GetMapping("/springjpa/{id}")
  public House getSpringJpaHouse(@PathVariable("id") Long id) {
    return springJpaRepository.findById(id).get();
  }

}
