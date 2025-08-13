package br.ufrn.plataformaproad.controller;


import br.ufrn.plataformaproad.security.JwtTokenProvider;
import br.ufrn.plataformaproad.service.CustomUserDetailsService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userService;

    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, CustomUserDetailsService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        userService.createUser(request.getUsername(), request.getPassword(), "USER");
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtTokenProvider.generateToken(auth.getName());
        return ResponseEntity.ok(token);
    }

    @Data
    public static class AuthRequest {
        private String username;
        private String password;
    }
}
