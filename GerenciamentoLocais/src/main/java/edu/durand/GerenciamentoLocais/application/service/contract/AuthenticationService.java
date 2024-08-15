package edu.durand.GerenciamentoLocais.application.service.contract;

import edu.durand.GerenciamentoLocais.application.dto.AuthenticationDTO;
import edu.durand.GerenciamentoLocais.application.dto.LoginResponseDTO;
import edu.durand.GerenciamentoLocais.application.dto.RegisterDTO;
import edu.durand.GerenciamentoLocais.domain.model.User;
import edu.durand.GerenciamentoLocais.infra.repository.UserRepository;
import edu.durand.GerenciamentoLocais.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenService tokenService;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository repository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenService = tokenService;
    }

    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.username()) != null) return ResponseEntity.badRequest().body("Este nome de usuario já existe");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.username(), encryptedPassword, data.role());

        this.repository.save(user);
        return ResponseEntity.ok().build();
    }
}
