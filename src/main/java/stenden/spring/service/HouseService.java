package stenden.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stenden.spring.data.jdbc.JdbcTemplateRepository;
import stenden.spring.data.model.House;

@Service
public class HouseService {

    private final JdbcTemplateRepository jdbcTemplateRepository;

    @Autowired
    public HouseService(JdbcTemplateRepository jdbcTemplateRepository) {
        this.jdbcTemplateRepository = jdbcTemplateRepository;
    }

    public House getTemplateJdbcHouse(Long id) {
        return jdbcTemplateRepository.getByID(id);
    }

}
