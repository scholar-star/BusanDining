package busan.dining.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    @NotBlank(message="사용자 ID는 필수로 입력해야 합니다.")
    private String userID;

    @NotBlank(message="사용자 password는 필수로 입력해야 합니다.")
    private String password;

    private String name;

    @NotBlank(message="이메일은 필수로 입력해야 합니다.")
    @Email
    private String email;

    private String location;
}
