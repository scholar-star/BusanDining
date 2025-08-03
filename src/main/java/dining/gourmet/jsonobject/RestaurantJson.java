package dining.gourmet.jsonobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantJson {
    public String textQuery;
    public float minRating;
    public String languageCode;
}
