package dining.gourmet.auth.Contoller;

import dining.gourmet.auth.DTO.LoginDTO;
import dining.gourmet.auth.DTO.UserDTO;
import dining.gourmet.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/busan/login")
    public String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping("/busan/signin")
    @ResponseBody
    public ResponseEntity<String> signin(@ModelAttribute LoginDTO loginDTO) {
        ResponseEntity<String> response;
        String token = userService.login(loginDTO);
        response = new ResponseEntity<>(token, HttpStatus.OK);
        return response;
    }

    @PostMapping("/busan/auth")
    public String auth(@ModelAttribute UserDTO userDTO) {
        userService.insertUser(userDTO);
        return "redirect:/busan/login";
    }

    @GetMapping("/busan/signup")
    public String signup(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "signup";
    }
}
