package stenden.spring.data.model;


import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
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
    @Size(max = 10) // This is just to test validation
    private String street;

    @Column(name = "CITY")
    private String city;

}
