package busan.dining.login;

import busan.dining.dto.SignupDTO;
import busan.dining.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class SignupController {
    private final AuthService authService;

    @Autowired
    public SignupController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/signup")
    public String signup(Model model) { // view Template(signup.html)에서 signupDTO를 전달하기로 결정이 되어 있기 때문에, GET에서 빈 객체라도 돌려줘야 함.
        model.addAttribute("signupDTO", new SignupDTO());
        return "busan/signup";
    }

    @PostMapping("/signupPost")
    public String signupPost(@Valid SignupDTO signupDTO, BindingResult bindingResult,
                             Model model) {
        if(bindingResult.hasErrors()) {
            return "busan/signup";
        }

        if(authService.signup(signupDTO) == HttpStatus.OK) {
            return "redirect:/login";
        } else {
            return "redirect:/signup";
        }
    }
}
