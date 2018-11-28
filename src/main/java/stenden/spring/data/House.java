package stenden.spring.data;

public interface House {

  public Long getId();

  public Integer getNrOfFloors();

  public Integer getNrOfRooms();

  public String getStreet();

  public String getCity();

  public void setId(Long id);

  public void setNrOfFloors(Integer nrOfFloors);

  public void setNrOfRooms(Integer nrOfRooms);

  public void setStreet(String street);

  public void setCity(String city);

}
