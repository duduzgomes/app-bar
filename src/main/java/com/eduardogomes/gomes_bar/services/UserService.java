package com.eduardogomes.gomes_bar.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eduardogomes.gomes_bar.dto.LoginRequest;
import com.eduardogomes.gomes_bar.dto.LoginResponse;
import com.eduardogomes.gomes_bar.entities.User;
import com.eduardogomes.gomes_bar.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Transactional
    public void createUser(LoginRequest loginRequest) throws Exception{
        var user = userRepository.findByUsername(loginRequest.username());

        if(user.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var newUser = new User();
        newUser.setUsername(loginRequest.username());
        newUser.setPassword(passwordEncoder.encode(loginRequest.password()));
        newUser.setDebts(new ArrayList<>());
        userRepository.save(newUser);
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(UUID id){
        userRepository.deleteById(id);
    }
    public LoginResponse login(LoginRequest loginRequest){
        var user = userRepository.findByUsername(loginRequest.username());

        if(user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)){
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }

        var experesIn = 300L;
        var now = Instant.now();

        var claims = JwtClaimsSet.builder()
            .issuer("gomes-bars")
            .subject(user.get().getId().toString())
            .issuedAt(now)
            .expiresAt(now.plusSeconds(experesIn))
            .build();
        
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, experesIn);


    }

}
