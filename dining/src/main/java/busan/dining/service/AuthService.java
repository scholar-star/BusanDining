package busan.dining.service;

import busan.dining.dto.LoginDTO;
import busan.dining.dto.SignupDTO;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.http.HttpStatus;

@Component
@Slf4j
public class AuthService {
    private final HikariDataSource hikariDataSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public HttpStatus login(LoginDTO user) {
        try {
            String sql = "SELECT * FROM userinfo WHERE ID = ? AND password = ?";
            Connection connection = hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            String crypted = passwordEncoder.encode(user.getPassword());
            preparedStatement.setString(1, user.getUserID());
            preparedStatement.setString(2, crypted);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) // 존재
                return HttpStatus.OK;
            else
                return HttpStatus.UNAUTHORIZED;
        } catch (SQLException e) {
            return HttpStatus.UNAUTHORIZED;
        }
    }

    public SignupDTO findUser(String userID) {
        try {
            String sql = "SELECT * FROM userinfo WHERE ID = ?";
            Connection connection = hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            SignupDTO signupDTO = new SignupDTO();
            signupDTO.setUserID(resultSet.getString("ID"));
            signupDTO.setPassword(resultSet.getString("password"));
            signupDTO.setName(resultSet.getString("name"));
            signupDTO.setEmail(resultSet.getString("email"));
            signupDTO.setLocation(resultSet.getString("location"));
            return signupDTO;

        } catch (SQLException e) {
            return null;
        }
    }
    
    public HttpStatus signup(SignupDTO user) {
        try {
            String sql = "INSERT INTO userinfo (ID, password, name, email, location) VALUES (?, ?, ?, ?, ?)";
            Connection connection = hikariDataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserID());

            String crypted = passwordEncoder.encode(user.getPassword());
            preparedStatement.setString(2, crypted);
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getLocation());
            preparedStatement.execute();
        } catch (SQLException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.OK;
    }
}
