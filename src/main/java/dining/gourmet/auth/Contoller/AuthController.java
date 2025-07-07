package dining.gourmet.auth.Contoller;

import dining.gourmet.auth.DTO.ResultDTO;
import dining.gourmet.auth.DTO.UserDTO;
import dining.gourmet.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/busan/login")
    public String login(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "login";
    }

    @PostMapping("/busan/signin")
    public String signin(@ModelAttribute UserDTO userDTO) {
        if(userService.loadUserByUsername(userDTO.getId())!=null) {
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @PostMapping("/busan/auth")
    public String auth(@ModelAttribute UserDTO userDTO) {
        ResultDTO result = userService.insertUser(userDTO);
        log.info(result.toString());
        if(result.isSuccess())
            return "redirect:/busan/dining";
        else {
            return "signup";
        }
    }

    @GetMapping("/busan/signup")
    public String signup(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "signup";
    }
}
