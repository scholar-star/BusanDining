package dining.gourmet.repositories;

import dining.gourmet.Entity.RegisterUser;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterUserRepository extends JpaRepository<RegisterUser, Long> {

}
