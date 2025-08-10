package dining.gourmet.auth.Contoller;

import dining.gourmet.auth.DTO.DongAPIDTO;
import dining.gourmet.auth.DTO.GuAPIDTO;
import dining.gourmet.auth.service.APIService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AddressAPIController {

    private final APIService apiService;

    public AddressAPIController(APIService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/api/gus")
    @ResponseBody
    public List<GuAPIDTO> AllGus() {
        return apiService.gus();
    }

    @GetMapping("/api/dongs/{guId}")
    @ResponseBody
    public List<DongAPIDTO> allDongs(@PathVariable int guId) {
        return apiService.dongs(guId);
    }
}
