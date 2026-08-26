package br.com.pedropareschi.collegemanagement.resources;

import br.com.pedropareschi.collegemanagement.dto.EmailDTO;
import br.com.pedropareschi.collegemanagement.security.JWTUtil;
import br.com.pedropareschi.collegemanagement.security.UserSS;
import br.com.pedropareschi.collegemanagement.services.AuthService;
import br.com.pedropareschi.collegemanagement.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @Operation(summary = "Refreshing token")
    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Forgot my password")
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
        service.sendNewPassword(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }
}