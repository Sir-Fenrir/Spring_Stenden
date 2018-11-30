package stenden.spring.data.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import stenden.spring.data.House;
import stenden.spring.data.HouseRepository;
import stenden.spring.data.model.POJOHouse;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcTemplateRepository implements HouseRepository {

  private static final String GET_HOUSE_BY_ID =
          "SELECT ID, NR_OF_FLOORS, NR_OF_ROOMS, STREET, CITY FROM HOUSES WHERE ID = ?";

  private JdbcOperations jdbcOperations;

  @Autowired
  public JdbcTemplateRepository(JdbcOperations jdbcOperations) {
    this.jdbcOperations = jdbcOperations;
  }

  public House getByID(Long id) {
    return jdbcOperations.queryForObject(GET_HOUSE_BY_ID, this::rowMapper, id);
  }

  private POJOHouse rowMapper(ResultSet rs, int rowNum) throws SQLException {
    return new POJOHouse(rs.getLong("ID"),
            rs.getInt("NR_OF_FLOORS"),
            rs.getInt("NR_OF_ROOMS"),
            rs.getString("STREET"),
            rs.getString("CITY"));
  }

}
