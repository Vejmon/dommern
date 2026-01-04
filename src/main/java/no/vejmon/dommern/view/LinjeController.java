package no.vejmon.dommern.view;


import no.vejmon.dommern.bane.Kusk;
import no.vejmon.dommern.judge.LinjeDommer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/linje")
public class LinjeController {

    private final LinjeDommer linjeDommer;

    @Autowired
    public LinjeController(LinjeDommer linjeDommer) {
        this.linjeDommer = linjeDommer;
    }

    @GetMapping
    public List<Kusk> getActiveKusker() {
        return linjeDommer.getKusker();
    }

}
