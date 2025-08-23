package dining.gourmet.auth.service;

import dining.gourmet.Entity.RegisterUser;
import dining.gourmet.Entity.Users;
import dining.gourmet.auth.DTO.*;
import dining.gourmet.exceptions.CustomServerException;
import dining.gourmet.jwt.JwtTokenUtil;
import dining.gourmet.repositories.RegisterUserRepository;
import dining.gourmet.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final AddressService addressService;
    private final RegisterUserRepository registerUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.secret}")
    private String secret;

    public Users DTOtoUserEntity(UserDTO userDTO) {
        Users users = Users.builder()
                .loginID(userDTO.getLoginID())
                .password(userDTO.getPassword())
                .nickname(userDTO.getPassword())
                .email(userDTO.getEmail())
                .role(userDTO.getUserType())
                .build();
        return users;
    }

    public RegisterUser DTOtoRegisterEntity(UserDTO userDTO) {
        RegisterUser registerUser = RegisterUser.builder()
                .gu(addressService.guEntity(userDTO.getCity()))
                .dong(addressService.dongEntity(userDTO.getDistrict()))
                .user(DTOtoUserEntity(userDTO))
                .build();
        return registerUser;
    }

    public Users insertUser(UserDTO userDTO) {
        try {
            String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
            userDTO.setPassword(encryptedPassword);
            if(userDTO.getCity()!=null && userDTO.getDistrict()!=null) {
                registerUserRepository.save(DTOtoRegisterEntity(userDTO));
            }
            return usersRepository.save(DTOtoUserEntity(userDTO));
        }
        catch (Exception e) {
            throw e;
        }
    }

    public String login(LoginDTO login) {
        String loginId = login.getLoginId();
        String password = login.getPassword();

        Users user = usersRepository.findByLoginID(loginId);
        if (user == null) {
            throw new UsernameNotFoundException(loginId);
        }

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Not Matched");
        }

        String token = jwtTokenUtil.createToken(loginId, user.getNickname(), user.getRole(), 30);
        if(token==null) {
            throw new CustomServerException("서버 오류");
        }
        return token;
    }
}
