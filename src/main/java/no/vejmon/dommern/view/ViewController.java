package no.vejmon.dommern.view;

import no.vejmon.dommern.judge.LinjeDommer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final LinjeDommer linjeDommer;

    public ViewController(LinjeDommer linjeDommer) {
        this.linjeDommer = linjeDommer;
    }

    @GetMapping({"/", "/index", ""})
    public String index(Model model) {
        model.addAttribute("kusker", linjeDommer.getKusker());
        return "dist/index";
    }
}
