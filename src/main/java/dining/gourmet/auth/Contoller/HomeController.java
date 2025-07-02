package dining.gourmet.auth.Contoller;

import dining.gourmet.auth.DTO.UserDTO;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/busan/dining")
    public String home(Model model) {
        return "home";
    }
}
