package busan.dining.service;

import busan.dining.dto.LoginDTO;
import busan.dining.dto.SignupDTO;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.http.HttpStatus;

@Component
public class AuthService {
    private final HikariDataSource hikariDataSource;
    private final PasswordEncoder passwordEncoder;

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
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.execute();
        } catch (SQLException e) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.OK; // 기본적인 DB 골조
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
