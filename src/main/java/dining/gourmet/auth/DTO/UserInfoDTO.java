package dining.gourmet.auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String id;
    private String password;
    private String passwordConfirm;
    private String nickname;
    private String email;
    private boolean role;

    public UserInfoDTO(String id, String password, String nickname, String email, boolean role) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }
}
