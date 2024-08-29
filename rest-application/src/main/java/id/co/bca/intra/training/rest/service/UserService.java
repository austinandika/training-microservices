package id.co.bca.intra.training.rest.service;

import id.co.bca.intra.training.rest.entity.User;
import id.co.bca.intra.training.rest.security.UserInfoDetails;
import id.co.bca.intra.training.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }

    public User getUserByIdOrUsername(Long id, String username) {
        return userRepository.findByIdOrUsername(id, username);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return new UserInfoDetails(user);
    }
}
