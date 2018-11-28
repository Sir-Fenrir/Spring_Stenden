package stenden.spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcRepository {

  private static final String GET_HOUSE_BY_ID =
          "SELECT ID, NR_OF_FLOORS, NR_OF_ROOMS, STREET, CITY FROM HOUSES WHERE ID = ?";

  private JdbcOperations jdbcOperations;

  @Autowired
  public JdbcRepository(JdbcOperations jdbcOperations) {
    this.jdbcOperations = jdbcOperations;
  }

  public JdbcHouse getByID(Long id) {
    return jdbcOperations.queryForObject(GET_HOUSE_BY_ID, this::rowMapper, id);
  }

  private JdbcHouse rowMapper(ResultSet rs, int rowNum) throws SQLException {
    return new JdbcHouse(rs.getLong("ID"),
            rs.getInt("NR_OF_FLOORS"),
            rs.getInt("NR_OF_ROOMS"),
            rs.getString("STREET"),
            rs.getString("CITY"));
  }

}
