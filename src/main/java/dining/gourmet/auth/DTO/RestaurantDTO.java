package dining.gourmet.auth.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    @JsonProperty("title")
    String title;

    @JsonProperty("link")
    String link;

    @JsonProperty("category")
    String category;

    @JsonProperty("description")
    String description;

    @JsonProperty("telephone")
    String telephone;

    @JsonProperty("address")
    String address;

    @JsonProperty("roadAddress")
    String roadAddress;

    @JsonProperty("mapx")
    Long mapx;

    @JsonProperty("mapy")
    Long mapy;
}
