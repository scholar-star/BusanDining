package dining.gourmet.auth.Contoller;

import dining.gourmet.auth.DTO.ResultDTO;
import dining.gourmet.auth.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/busan/restaurants")
    @ResponseBody
    public ResultDTO RestaurantController() {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO = restaurantService.restaurants();
            resultDTO.setSuccess(Boolean.TRUE);
            resultDTO.setMessage("Success");
        } catch(Exception e) {
            resultDTO.setSuccess(Boolean.FALSE);
            resultDTO.setMessage(e.getMessage());
        }
        return resultDTO;
    }
}
