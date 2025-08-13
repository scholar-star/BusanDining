package dining.gourmet.auth.service;

import dining.gourmet.auth.DTO.*;
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

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.secret}")
    private String secret;

    public ResultDTO insertUser(UserDTO userDTO) {
        UserInfoDTO userInfoDTO = new UserInfoDTO(
                userDTO.getId(), userDTO.getPassword(), userDTO.getUsername(), userDTO.getEmail(), false);
        try {
            String encryptedPassword = passwordEncoder.encode(userInfoDTO.getPassword());
            String insertSql = "insert into user_info(username, login_id, password, email, role) values (?, ?, ?, ?, 0)";
            jdbcTemplate.update(
                    insertSql, userInfoDTO.getUsername(), userInfoDTO.getId(), encryptedPassword, userInfoDTO.getEmail());

            User user = loadUserByUsername(userInfoDTO.getUsername());
            int userId = user.getId();

            UserAddressDTO userAddressDTO = new UserAddressDTO(
                    userId, userDTO.getDistrict()
            );

            String addressSql = "insert into user_address(user_id, live_dong_id) values (?,?)";
            jdbcTemplate.update(
                    addressSql, userId, userAddressDTO.getDong()
            );
            return new ResultDTO(true, "Data Insert Success");
        }
        catch (Exception e) {
            return new ResultDTO(false, "Inserted user failed");
        }
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "select * from user_info where login_id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> {
                        int id = rs.getInt("id");
                        String nickname = rs.getString("username");
                        String loginId = rs.getString("login_id");
                        String password = rs.getString("password");
                        String email = rs.getString("email");
                        boolean role = rs.getBoolean("role");
                        return new User(new UserInfoDTO(loginId, password, nickname, email, role), id);
                    }, username);
            return user;
            // 중간 옵션은 바인딩에 들어갈 변수
        } catch(Exception e) {
            return null;
        }
    }

    public JwtDTO login(LoginDTO login) {
        String loginId = login.getLoginId();
        String password = login.getPassword();

        UserDetails user = loadUserByUsername(loginId);
        if (user == null) {
            throw new UsernameNotFoundException(loginId);
        }

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Not Matched");
        }

        JwtDTO jwt = new JwtDTO();
        jwt.setToken(jwtTokenUtil.createToken(loginId, 30));
        jwt.setSuccess(Boolean.TRUE);
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
