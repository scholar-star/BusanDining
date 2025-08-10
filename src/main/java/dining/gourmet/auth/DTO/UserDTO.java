package dining.gourmet.auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String id;
    private String password;
    private String passwordConfirm;
    private String email;
    private Integer city;
    private Integer district;
}
