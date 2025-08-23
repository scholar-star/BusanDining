package dining.gourmet.Entity;

import dining.gourmet.auth.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String loginID;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private UserType role;
}
