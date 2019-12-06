package stenden.spring.data;

public interface HouseRepository {

    House getByID(Long id);

    House addHouse(House house);
}
