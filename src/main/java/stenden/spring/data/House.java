package stenden.spring.data;

public interface House {

    Long getId();

    void setId(Long id);

    Integer getNrOfFloors();

    void setNrOfFloors(Integer nrOfFloors);

    Integer getNrOfRooms();

    void setNrOfRooms(Integer nrOfRooms);

    String getStreet();

    void setStreet(String street);

    String getCity();

    void setCity(String city);

}
