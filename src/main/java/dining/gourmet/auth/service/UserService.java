package dining.gourmet.auth.service;

import dining.gourmet.auth.DTO.ResultDTO;
import dining.gourmet.auth.DTO.UserDTO;
import dining.gourmet.auth.UserType;
import dining.gourmet.auth.details.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public ResultDTO insertUser(UserDTO userDTO) {
        ResultDTO resultDTO = null;
        try {
            String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
            String sql = "insert into users(nickname, login_id, password) values (?, ?, ?)";
            jdbcTemplate.update(sql, userDTO.getNickname(), userDTO.getId(), encryptedPassword);
            return new ResultDTO(true, "Inserted user successfully");
        }
        catch (Exception e) {
            return new ResultDTO(false, "Inserted user failed");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "select nickname, login_id, password from users where username = ?";
        UserDetails user = jdbcTemplate.queryForObject(sql, new Object[]{username},
                (rs, rowNum) -> {
                    String nickname = rs.getString("nickname");
                    String loginId = rs.getString("login_id");
                    String password = rs.getString("password");
                    return new User(new UserDTO(loginId, password, nickname, UserType.USER));
                });
                // 중간 옵션은 바인딩(?)에 들어갈 변수
        if(user == null) {
            throw new UsernameNotFoundException("User not found: "+username);
        }
        return user;
    }
}
