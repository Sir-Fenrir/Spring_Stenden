package stenden.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import stenden.spring.data.House;
import stenden.spring.data.HouseRepository;
import stenden.spring.data.jpa.SpringJpaRepository;
import stenden.spring.data.model.POJOHouse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HouseServiceTest {

    @Mock
    private HouseRepository jdbcTemplateRepository;

    @Mock
    private HouseRepository pureJdbcRepository;

    @Mock
    private HouseRepository entityManagerJpaRepository;

    @Mock
    private SpringJpaRepository springJpaRepository;

    @InjectMocks
    private HouseService houseService;

//    @BeforeEach
//    public void setup() {
//        jdbcTemplateRepository = mock(HouseRepository.class);
//        pureJdbcRepository = mock(HouseRepository.class);
//        entityManagerJpaRepository = mock(HouseRepository.class);
//        springJpaRepository = mock(SpringJpaRepository.class);
//        houseService = new HouseService(jdbcTemplateRepository, pureJdbcRepository, hibernateRepository, entityManagerJpaRepository, springJpaRepository);
//    }

    @Test
    public void testGetTemplateJdbcHouse() {
        POJOHouse value = new POJOHouse(1L, 2, 3, "Street", "City");
        when(jdbcTemplateRepository.getByID(anyLong())).thenReturn(value);
        House templateJdbcHouse = houseService.getTemplateJdbcHouse(1L);

        assertThatThrownBy(() -> {
            throw new Exception("Oh no");
        }).hasMessage("Oh no2").isOfAnyClassIn(Exception.class);
    }

}
