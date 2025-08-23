package dining.gourmet.auth.details;

import dining.gourmet.auth.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Member implements UserDetails {
    private String nickname;
    private String loginID;
    private UserType userType;

    public Member(String nickname, String loginID, UserType userType) {
        this.nickname = nickname;
        this.loginID = loginID;
        this.userType = userType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (userType == UserType.ADMIN)
            authorities.add(new SimpleGrantedAuthority(UserType.ADMIN.name()));
        else
            authorities.add(new SimpleGrantedAuthority(UserType.USER.name()));
        // User의 역할을 SingletonList로 변환
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getLoginId() {
        return this.loginID;
    }
}
