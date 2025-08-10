package dining.gourmet.auth.service;

import dining.gourmet.auth.DTO.DongAPIDTO;
import dining.gourmet.auth.DTO.GuAPIDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class APIService {
    private final JdbcTemplate jdbcTemplate;

    public List<GuAPIDTO> gus() {
        String sql = "select * from gu";
        try {
            List<GuAPIDTO> allgus = jdbcTemplate.query(sql,
                    (rs, rowNum) -> new GuAPIDTO(
                            rs.getInt("id"),
                            rs.getString("gu_name")
                    )
            );
            return allgus;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DongAPIDTO> dongs(int guId) {
        String sql = "select * from dong where gu_id = ?";
        try {
            List<DongAPIDTO> alldongs = jdbcTemplate.query(sql,
                    new Object[]{guId},
                    (rs, rowNum) -> new DongAPIDTO(
                            rs.getInt("id"),
                            rs.getInt("gu_id"),
                            rs.getString("dong")
                    ));
            return alldongs;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
