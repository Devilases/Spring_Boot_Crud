package zhelonin.hm3.servlet;

import org.springframework.web.bind.annotation.RequestMapping;
import zhelonin.hm3.entity.Teacher;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class TeacherRestController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @GetMapping(produces = "application/json")
  public String greeting() {
    return "Kersa";
  }


}
