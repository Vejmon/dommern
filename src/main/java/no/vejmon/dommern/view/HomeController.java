package no.vejmon.dommern.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {


    @GetMapping(value = {"", "/"})
    public String index(String name, Model model){
        return "index";
    }

}
