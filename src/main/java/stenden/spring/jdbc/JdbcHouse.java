package stenden.spring.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JdbcHouse {

  private Long id;

  private Integer nrOfFloors;

  private Integer nrOfRooms;

  private String street;

  private String city;

}
