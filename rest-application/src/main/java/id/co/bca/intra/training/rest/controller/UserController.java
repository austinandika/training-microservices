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
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User> insertUser(@RequestBody InsertUserRequestDTO request) {
        boolean userExists = userService.getUserByIdOrUsername(request.getId(), request.getUsername()) != null;

        if (userExists) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // encode password
        String encodedPassword = encoderUtilities.encoder().encode(request.getPassword());

        User user = new User(request.getId(), request.getUsername(), encodedPassword);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED) ;
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginUserResponseDTO> loginUser(@RequestBody LoginUserRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(new LoginUserResponseDTO(jwtService.generateToken(request.getUsername())), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
}
