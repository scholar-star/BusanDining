package busan.dining.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @NotBlank(message = "ID를 입력해주세요")
    private String userID;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
