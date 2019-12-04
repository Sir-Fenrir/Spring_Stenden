package stenden.spring.data.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import stenden.spring.data.model.House;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcTemplateRepository {

    private static final String GET_HOUSE_BY_ID =
            "SELECT ID, NR_OF_FLOORS, NR_OF_ROOMS, STREET, CITY FROM HOUSES WHERE ID = ?";

    private JdbcTemplate jdbcOperations;

    @Autowired
    public JdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcOperations = jdbcTemplate;
    }

    public House getByID(Long id) {
        return jdbcOperations.queryForObject(GET_HOUSE_BY_ID, this::rowMapper, id);
    }

    private House rowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new House(rs.getLong("ID"),
                rs.getInt("NR_OF_FLOORS"),
                rs.getInt("NR_OF_ROOMS"),
                rs.getString("STREET"),
                rs.getString("CITY"));
    }

}
