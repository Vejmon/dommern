package no.vejmon.dommern

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration)
@SpringBootTest
class DommernApplicationTests {

    @Test
    void contextLoads() {
    }

}
