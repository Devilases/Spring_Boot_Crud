package zhelonin.hm3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/welcome")
public class HelloController {

  @GetMapping
  public String printWelcome(ModelMap model) {

    model.addAttribute("message", "Spring 3 MVC - Hello World");
    return "hello";

  }
}
