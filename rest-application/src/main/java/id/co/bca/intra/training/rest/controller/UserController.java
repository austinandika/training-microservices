package id.co.bca.intra.training.rest.controller;

import id.co.bca.intra.training.rest.dto.userDTO.InsertUserRequestDTO;
import id.co.bca.intra.training.rest.dto.userDTO.LoginUserRequestDTO;
import id.co.bca.intra.training.rest.dto.userDTO.LoginUserResponseDTO;
import id.co.bca.intra.training.rest.entity.User;
import id.co.bca.intra.training.rest.service.JwtService;
import id.co.bca.intra.training.rest.service.UserService;
import id.co.bca.intra.training.rest.utilities.EncoderUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EncoderUtilities encoderUtilities;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User insertUser(@RequestBody InsertUserRequestDTO request) {
        boolean userExists = userService.getUserByIdOrUsername(request.getId(), request.getUsername()) != null;

        if (userExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with the same id or username already exists");
        }

        // encode password
        String encodedPassword = encoderUtilities.encoder().encode(request.getPassword());

        User user = new User(request.getId(), request.getUsername(), encodedPassword);
        return userService.save(user);
    }

    @PostMapping("/api/auth/login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginUserResponseDTO loginUser(@RequestBody LoginUserRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return new LoginUserResponseDTO(jwtService.generateToken(request.getUsername()));
        }

        throw new UsernameNotFoundException("Invalid user request!");
    }

    @GetMapping("/api/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userService.getUsers();
    }
}
