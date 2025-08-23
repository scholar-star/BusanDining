package dining.gourmet.auth.DTO;

import dining.gourmet.auth.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String loginID;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    private Integer city;

    private Integer district;

    @Builder.Default UserType userType = UserType.USER;
}
