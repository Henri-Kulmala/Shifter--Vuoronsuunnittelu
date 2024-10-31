package app.shifter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {
    @GetMapping("/login")
    public String loginPage() {
        return "login";  
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index"; 
    }
}

