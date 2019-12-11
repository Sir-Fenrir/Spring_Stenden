package stenden.spring.data.jpa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import stenden.spring.config.TestDatabaseConfig;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDatabaseConfig.class)
public class EntityManagerJpaRepositoryTest {

    @Autowired
    private EntityManagerJpaRepository repository;

    @Test
    public void test() {
        assertThat(repository).isNotNull();
    }

}
