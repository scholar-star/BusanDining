package busan.dining.create;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class CreateController {
    @GetMapping("/add")
    public String add() {
        return "busan/writing";
    }
}
