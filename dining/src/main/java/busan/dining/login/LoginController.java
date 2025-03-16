package busan.dining.login;

import busan.dining.jwt.jwtTokenUtil;
import busan.dining.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import busan.dining.dto.LoginDTO;

@Controller
@Slf4j
public class LoginController {
    private final AuthService authService;

    @Autowired
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "busan/login";
    }

    @PostMapping("/loginPost")
    public String loginPost(@Valid LoginDTO loginDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "busan/login";
        }

        if(authService.login(loginDTO)== HttpStatus.OK) {
            String jwt = jwtTokenUtil.createToken(loginDTO.getUserID(), "valuablekeyisthatiwantedtogarrentthatisntit123456789");
            model.addAttribute("jwt", jwt);
            return "redirect:/home";
        }
        else
            return "busan/login";
    }
}
