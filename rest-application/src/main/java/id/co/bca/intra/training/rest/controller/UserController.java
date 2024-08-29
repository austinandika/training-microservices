package id.co.bca.intra.training.rest.controller;

import id.co.bca.intra.training.rest.dto.userDTO.InsertUserRequestDTO;
import id.co.bca.intra.training.rest.dto.userDTO.LoginUserRequestDTO;
import id.co.bca.intra.training.rest.dto.userDTO.LoginUserResponseDTO;
import id.co.bca.intra.training.rest.entity.User;
import id.co.bca.intra.training.rest.responseEntity.CustomResponseEntity;
import id.co.bca.intra.training.rest.service.JwtService;
import id.co.bca.intra.training.rest.service.UserService;
import id.co.bca.intra.training.rest.utilities.EncoderUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> insertUser(@RequestBody InsertUserRequestDTO request) {
        boolean userExists = userService.getUserByIdOrUsername(request.getId(), request.getUsername()) != null;

        if (userExists) {
            return CustomResponseEntity.generateResponse(HttpStatus.BAD_REQUEST, false, "User already exists", null);
        }

        // encode password
        String encodedPassword = encoderUtilities.encoder().encode(request.getPassword());

        User user = new User(request.getId(), request.getUsername(), encodedPassword);

        return CustomResponseEntity.generateResponse(HttpStatus.CREATED, true, "", userService.save(user));
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginUserRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            return CustomResponseEntity.generateResponse(HttpStatus.FORBIDDEN, false, "Username or password incorrect", null);
        }

        return CustomResponseEntity.generateResponse(HttpStatus.OK, true, "", new LoginUserResponseDTO(jwtService.generateToken(request.getUsername())));
    }

    @GetMapping("/api/users")
    public ResponseEntity<Object> getUsers() {
        return CustomResponseEntity.generateResponse(HttpStatus.OK, true, "", userService.getUsers());
    }
}
