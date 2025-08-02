package dining.gourmet.auth.service;

import dining.gourmet.auth.DTO.JWTDto;
import dining.gourmet.auth.DTO.LoginDTO;
import dining.gourmet.auth.DTO.ResultDTO;
import dining.gourmet.auth.DTO.UserDTO;
import dining.gourmet.auth.UserType;
import dining.gourmet.auth.details.User;
import dining.gourmet.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.secret}")
    private String secret;

    public ResultDTO insertUser(UserDTO userDTO) {
        try {
            String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
            String sql = "insert into users(username, login_id, password) values (?, ?, ?)";
            jdbcTemplate.update(sql, userDTO.getNickname(), userDTO.getId(), encryptedPassword);
            return new ResultDTO(true, "Inserted user successfully");
        }
        catch (Exception e) {
            return new ResultDTO(false, "Inserted user failed");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "select * from users where login_id = ?";
        try {
            UserDetails user = jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> {
                        String nickname = rs.getString("username");
                        String loginId = rs.getString("login_id");
                        String password = rs.getString("password");
                        String email = rs.getString("email");
                        String province = rs.getString("province");
                        String city = rs.getString("city");
                        String district = rs.getString("district");
                        return new User(new UserDTO(loginId, password, nickname, email, province, city, district, UserType.USER));
                    }, username);
            return user;
            // 중간 옵션은 바인딩에 들어갈 변수
        } catch(Exception e) {
            return null;
        }
    }

    public JWTDto login(LoginDTO login) {
        String loginId = login.getLoginId();
        String password = login.getPassword();

        UserDetails user = loadUserByUsername(loginId);
        if (user == null) {
            throw new UsernameNotFoundException(loginId);
        }

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Not Matched");
        }

        JWTDto jwt = new JWTDto();
        jwt.setToken(jwtTokenUtil.createToken(loginId, 30));
        return jwt;
    }

    public ResultDTO Validate(String token, String loginId) {
        ResultDTO result = null;
        if(jwtTokenUtil.validateToken(token, loginId))
            result = new ResultDTO(true, "Successfully logged in");
        else
            result = new ResultDTO(false, "Invalid token");
        return result;
    }
}
