package stenden.spring.data.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stenden.spring.data.House;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JdbcHouse implements House {

  private Long id;

  private Integer nrOfFloors;

  private Integer nrOfRooms;

  private String street;

  private String city;

}
