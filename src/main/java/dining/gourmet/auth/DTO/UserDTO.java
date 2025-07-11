package dining.gourmet.auth.DTO;

import dining.gourmet.auth.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String password;
    private String passwordConfirm;
    private String nickname;
    private String email;
    private String province;
    private String city;
    private String district;
    private UserType role;

    public UserDTO(String id, String password, String nickname, String email, String province, String city, String district, UserType role) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.province = province;
        this.city = city;
        this.district = district;
        this.role = role;
    }
}
