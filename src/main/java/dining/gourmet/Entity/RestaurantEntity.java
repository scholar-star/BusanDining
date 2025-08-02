package dining.gourmet.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantEntity {
    String name;
    String province;
    String city;
    String district;
    String telephone;
    Long mapx;
    Long mapy;
    String description;
    int rate;
}

