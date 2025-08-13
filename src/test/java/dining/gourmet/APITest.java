package dining.gourmet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
public class APITest {
    @Value("${api.pubSecretKey}")
    private String pubSecretKey;

    @Test
    void test() {
        WebClient pubAPIClient = WebClient.builder()
                .baseUrl("http://apis.data.go.kr")
                .build();

        Mono<String> pubMono = pubAPIClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/6260000/FoodService/getFoodKr")
                        .queryParam("ServiceKey", pubSecretKey)
                        .queryParam("pageNo",1)
                        .queryParam("numOfRows",200)
                        .queryParam("resultType","json")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
