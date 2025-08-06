package dining.gourmet.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminAccountInsert implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    @Value("${admin.id}")
    private String adminId;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    public AdminAccountInsert(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            String checkSql = "SELECT COUNT(*) FROM users WHERE login_id = ?";
            Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, adminId);
            log.info(count + " rows affected");

            if(count == 0 && count != null) {
                String encodedPassword = passwordEncoder.encode(adminPassword);
                String sql = "insert into users(username, login_id, password, role) values (?, ?, ?, ?)";
                jdbcTemplate.update(sql, "관리자", adminId, encodedPassword, true);
            }
        } catch(Exception e) {
            log.info(e.getMessage());
        }
    }
}
