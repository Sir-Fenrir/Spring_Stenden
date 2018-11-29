package stenden.spring.data.hibernate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stenden.spring.data.House;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HOUSES")
@Entity
public class HibernateHouse implements House {

  @Id
  private Long id;

  @Column(name = "NR_OF_FLOORS")
  private Integer nrOfFloors;

  @Column(name = "NR_OF_ROOMS")
  private Integer nrOfRooms;

  @Column(name = "STREET")
  private String street;

  @Column(name = "CITY")
  private String city;

}