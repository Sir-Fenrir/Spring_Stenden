package stenden.spring.data.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stenden.spring.data.House;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "HOUSES")
@Entity
public class AnnotatedHouse implements House {

    @ManyToMany
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long ID;

    @Column(name = "NR_OF_FLOORS")
    private Integer nrOfFloors;

    @Column(name = "NR_OF_ROOMS")
    private Integer nrOfRooms;

    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY")
    @Size(max = 5)
    private String city;

}
