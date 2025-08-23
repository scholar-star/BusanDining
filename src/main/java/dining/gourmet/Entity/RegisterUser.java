package dining.gourmet.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private Users user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="gu_id")
    private Gu gu;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="dong_id")
    private Dong dong;
}
