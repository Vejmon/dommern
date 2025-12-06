package no.vejmon.dommern

import org.springframework.boot.SpringApplication

class TestDommernApplication {

    static void main(String[] args) {
        SpringApplication.from(DommernApplication::main).with(TestcontainersConfiguration).run(args)
    }

}
