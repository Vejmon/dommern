package no.vejmon.dommern.lyttere;

import jakarta.annotation.PostConstruct;
import no.vejmon.dommern.bane.Kusk;
import no.vejmon.dommern.bane.KuskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LinjeDommer {

    public static Kusk kusk1 = new Kusk("bane1");
    public static Kusk kusk2 = new Kusk("bane2");
    public static Kusk kusk3 = new Kusk("bane3");
    public static Kusk kusk4 = new Kusk("bane4");
    public static List<Kusk> kusker = new ArrayList<>(Arrays.asList(
            kusk1, kusk2, kusk3, kusk4
    ));

    private final KuskService kuskService;

    public LinjeDommer(KuskService kuskService) {
        this.kuskService = kuskService;
    }

    @PostConstruct
    public void init() {
        for (Kusk kusk : kusker) {
            kusk = kuskService.findByName(kusk.getName());
        }


    }

}
