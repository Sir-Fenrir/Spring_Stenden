package stenden.spring.data.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import stenden.spring.data.House;
import stenden.spring.data.HouseRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Repository
public class PureJdbcRepository implements HouseRepository {

  private static final String GET_HOUSE_BY_ID =
          "SELECT ID, NR_OF_FLOORS, NR_OF_ROOMS, STREET, CITY FROM HOUSES WHERE ID = ?";

  private DataSource dataSource;

  @Autowired
  public PureJdbcRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public House getByID(Long id) {
    try (
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = createPreparedStatement(connection, id);
            ResultSet resultSet = statement.executeQuery()
    ) {
      resultSet.first();
      return new JdbcHouse(
              resultSet.getLong("ID"),
              resultSet.getInt("NR_OF_FLOORS"),
              resultSet.getInt("NR_OF_ROOMS"),
              resultSet.getString("STREET"),
              resultSet.getString("CITY")
      );
    } catch (SQLException e) {
      // Any kind of exception thrown is an SQLException. So it could be anything...
      log.error("The query failed!", e);
    }

    return null;
  }

  private PreparedStatement createPreparedStatement(Connection connection, Long id) throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement(GET_HOUSE_BY_ID)) {
      statement.setLong(1, id);
      return statement;
    }
  }
}
