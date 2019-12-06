package stenden.spring.data.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stenden.spring.data.House;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HOUSES")
@Entity
public class AnnotatedHouse implements House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
