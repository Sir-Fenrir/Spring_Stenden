package stenden.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stenden.spring.data.House;
import stenden.spring.data.HouseRepository;
import stenden.spring.data.model.AnnotatedHouse;

@Service
public class HouseService {

  private final HouseRepository entityManagerJpaRepository;

  @Autowired
  public HouseService(HouseRepository entityManagerJpaRepository) {
    this.entityManagerJpaRepository = entityManagerJpaRepository;
  }

  public House getJpaHouse(Long id) {
    return entityManagerJpaRepository.getByID(id);
  }

  public House addHouse(AnnotatedHouse house) {
    return entityManagerJpaRepository.addHouse(house);
  }
}

