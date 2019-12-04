package stenden.spring.data.model;

public class House {

    private Long id;

    private Integer nrOfFloors;

    private Integer nrOfRooms;

    private String street;

    private String city;

    public House() {
    }

    public House(Long id, Integer nrOfFloors, Integer nrOfRooms, String street, String city) {
        this.id = id;
        this.nrOfFloors = nrOfFloors;
        this.nrOfRooms = nrOfRooms;
        this.street = street;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNrOfFloors() {
        return nrOfFloors;
    }

    public void setNrOfFloors(Integer nrOfFloors) {
        this.nrOfFloors = nrOfFloors;
    }

    public Integer getNrOfRooms() {
        return nrOfRooms;
    }

    public void setNrOfRooms(Integer nrOfRooms) {
        this.nrOfRooms = nrOfRooms;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
