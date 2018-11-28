package stenden.spring.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stenden.spring.data.House;
import stenden.spring.data.HouseRepository;

// We're telling Spring MVC that this is a REST controller
// This treats methods with @RequestMapping as if it had the @ResponseBody annotation
// which tells Spring that the return value should be transformed into a response
@RestController
// The base request mapping this controller listens to, we respond to anything starting with /data
@RequestMapping("/data")
public class DataResource {

  private final HouseRepository jdbcRepository;
  private final HouseRepository hibernateRepository;

  @Autowired
  public DataResource(HouseRepository jdbcRepository, HouseRepository hibernateRepository) {
    this.jdbcRepository = jdbcRepository;
    this.hibernateRepository = hibernateRepository;
  }

  @GetMapping("/jdbc/{id}")
  public House getJdbcHouse(@PathVariable("id") Long id) {
    return jdbcRepository.getByID(id);
  }

  @GetMapping("/hibernate/{id}")
  public House getHibernateHouse(@PathVariable("id") Long id) {
    return hibernateRepository.getByID(id);
  }

}
