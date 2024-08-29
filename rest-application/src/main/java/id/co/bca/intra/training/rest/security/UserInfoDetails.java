package id.co.bca.intra.training.rest.security;

import id.co.bca.intra.training.rest.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserInfoDetails implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(User user) {
        this.username = user.getUsername(); // Assuming 'name' is used as 'username'
        this.password = user.getPassword();
        this.authorities = Stream.of("member")
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
