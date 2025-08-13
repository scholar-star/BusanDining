package dining.gourmet.auth.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dining.gourmet.Entity.RestaurantEntity;
import dining.gourmet.auth.DTO.RestaurantDTO;
import dining.gourmet.auth.DTO.ResultDTO;
import dining.gourmet.jsonobject.RestaurantJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.gooSecretKey}")
    private String googleSecret;

    @Value("${api.pubSecretKey}")
    private String pubSecret;

    public List<RestaurantEntity> dtoToEntity(String jsonStr) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonStr);
            JsonNode itemNode = rootNode.path("items");
            String itemData = itemNode.toString();
            List<RestaurantDTO> itemList = objectMapper.readValue(itemData, new TypeReference<List<RestaurantDTO>>() {});

            List<RestaurantEntity> restsList = new ArrayList<>();
            for (RestaurantDTO item : itemList) {
                String province = item.getAddress().split(" ")[0];
                String city = item.getAddress().split(" ")[1];
                String district = item.getAddress().split(" ")[2];
                RestaurantEntity entity = new RestaurantEntity(item.getTitle(),
                province, city, district, item.getTelephone(), item.getMapx(), item.getMapy(),
                        item.getDescription(), 0);
                restsList.add(entity);
            }
            return restsList;
        } catch(Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public ResultDTO insertRestaurant(List<RestaurantEntity> restaurantEntities) {
        try {
            for (RestaurantEntity restaurantEntity : restaurantEntities) {
                String sql = "insert into restaurants(name, province, city, district, telephone, mapx, mapy, description, rate) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                jdbcTemplate.update(sql, restaurantEntity.getName(),
                        restaurantEntity.getProvince(),
                        restaurantEntity.getCity(),
                        restaurantEntity.getDistrict(),
                        restaurantEntity.getTelephone(),
                        restaurantEntity.getMapx(),
                        restaurantEntity.getMapy(),
                        restaurantEntity.getDescription(),
                        restaurantEntity.getRate());
            }
            return new ResultDTO(true, "Insert Successfully");
        } catch(Exception e) {
            return new ResultDTO(false, e.getMessage());
        }
    }

    public ResultDTO restaurants() {
        String query = "부산 맛집";

        RestaurantJson json = new RestaurantJson();
        json.setTextQuery(query);
        json.setLanguageCode("ko");
        json.setMinRating(4.0f);

        WebClient pubAPIClient = WebClient.builder()
                .baseUrl("http://apis.data.go.kr")
                .build();

        Mono<String> pubMono = pubAPIClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/6260000/FoodService/getFoodKr")
                        .queryParam("ServiceKey", pubSecret)
                        .queryParam("pageNo",1)
                        .queryParam("numOfRows",200)
                        .queryParam("resultType","json")
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        // block()을 사용하면 동기적으로 응답을 받을 수도 있음
        String result = pubMono.block();
        log.info(result);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(result);
            JsonNode resultsNode = rootNode.get("results");
            List<RestaurantEntity> restaurants = dtoToEntity(result);
            ResultDTO resultDTO = insertRestaurant(restaurants);
            return resultDTO;
        } catch(Exception e) {
            return new ResultDTO(false, e.getMessage());
        }
    }

    public List<RestaurantEntity> getAllRestaurants() {
        try {
            String sql = "select * from restaurants";
            List<RestaurantEntity> restaurants = jdbcTemplate.query(sql,
                    (rs, rowNum) -> {
                        String resName = rs.getString("name");
                        String resProvince = rs.getString("province");
                        String resCity = rs.getString("city");
                        String resDistrict = rs.getString("district");
                        String resTelephone = rs.getString("telephone");
                        long resMapx = rs.getLong("mapx");
                        long resMapy = rs.getLong("mapy");
                        String resDescription = rs.getString("description");
                        int resRate = rs.getInt("rate");
                        return new RestaurantEntity(resName, resProvince, resCity, resDistrict, resTelephone, resMapx, resMapy, resDescription, resRate);
                    }
            );
            return restaurants;
        } catch (Exception e) {
            return null;
        }
    }
}
