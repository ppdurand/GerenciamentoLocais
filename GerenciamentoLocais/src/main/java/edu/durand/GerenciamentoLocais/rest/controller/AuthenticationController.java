package edu.durand.GerenciamentoLocais.rest.controller;

import edu.durand.GerenciamentoLocais.application.dto.AuthenticationDTO;
import edu.durand.GerenciamentoLocais.application.dto.LoginResponseDTO;
import edu.durand.GerenciamentoLocais.application.dto.RegisterDTO;
import edu.durand.GerenciamentoLocais.application.service.contract.AuthenticationService;
import edu.durand.GerenciamentoLocais.domain.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        return this.authenticationService.login(data);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        return this.authenticationService.register(data);
    }
}
