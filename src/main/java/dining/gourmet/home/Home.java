package dining.gourmet.home;

import dining.gourmet.auth.DTO.UserDTO;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {
    @GetMapping("/busan/dining")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/busan/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/busan/signup")
    public String signup(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "signup";
    }
}
