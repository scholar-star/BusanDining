package dining.gourmet.repositories;

import dining.gourmet.Entity.Restaurants;
import dining.gourmet.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurants, Long> {

}
