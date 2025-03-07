package busan.dining.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeMenuController {
    @GetMapping("/home")
    public String home() {
        return "busan/home";
    }
}
