package com.api.login.auth.services;

import com.api.login.auth.domain.User;
import com.api.login.auth.dto.ResponseDTO;
import com.api.login.auth.dto.LoginRequestDTO;
import com.api.login.auth.dto.RegisterRequestDTO;
import com.api.login.auth.exceptions.RegraDeNegocioException;
import com.api.login.auth.infra.security.TokenService;
import com.api.login.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public ResponseDTO login(LoginRequestDTO body) throws RegraDeNegocioException {

        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return new ResponseDTO(user.getName(), token);
        }

        throw new RegraDeNegocioException("Senha inválida.");
    }

    public ResponseDTO register(RegisterRequestDTO body) throws RegraDeNegocioException {

        Optional<User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return new ResponseDTO(newUser.getName(), token);
        }

        throw new RegraDeNegocioException("Usuário já existe para o email informado.");
    }
}
