package com.FinalYearProject.GarbageCollectionMS.auth;

import  com.FinalYearProject.GarbageCollectionMS.config.JwtService;
import com.FinalYearProject.GarbageCollectionMS.entity.users.*;
import com.FinalYearProject.GarbageCollectionMS.entity.users.Visible.Driver;
import com.FinalYearProject.GarbageCollectionMS.entity.users.Visible.HouseHolder;
import com.FinalYearProject.GarbageCollectionMS.entity.users.Visible.Supervisor;
import com.FinalYearProject.GarbageCollectionMS.repo.*;
import com.FinalYearProject.GarbageCollectionMS.token.Token;
import com.FinalYearProject.GarbageCollectionMS.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final HouseHolderRepository houseHolderRepository;
    private final DriverRepository driverRepository;
    private final SupervisorRepository supervisorRepository;

    public AuthenticationResponse register(RegisterRequest request){
        User user=null;
        if(request.getRole()== Role.SUPERVISOR){
            user=new Supervisor();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setNicNo(request.getNicNo());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());

            supervisorRepository.save((Supervisor) user);
        }
        if(request.getRole()== Role.HOUSEHOLDER){
            user=new HouseHolder();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setNicNo(request.getNicNo());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());

            houseHolderRepository.save((HouseHolder) user);
        }
        if(request.getRole()==Role.ADMIN){
            user=new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setNicNo(request.getNicNo());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());


            userRepository.save(user);
        }
        if(request.getRole()==Role.DRIVER){
            user=new Driver();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setNicNo(request.getNicNo());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());


            driverRepository.save((Driver) user);
        }

        //userRepository.save(user);
        var jwtToken=jwtService.generateToken(user);
        var refreshToken=jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,jwtToken);
        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getNicNo(),
                        request.getPassword()
                )
        );
        User user=userRepository.findByNicNo(request.getNicNo())
                .orElseThrow();
        var accessToken=jwtService.generateToken(user);
        var refreshToken=jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).role(user.getRole()).build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String nicNo;
//        if(authHeader==null||!authHeader.startsWith("Bearer ")){
//            return;
//        }
//        refreshToken=authHeader.substring(7);
//        nicNo=jwtService.extractUsername(refreshToken);
//        if(nicNo!=null){
//            var user=this.userRepository.findByNicNo(nicNo).orElseThrow();
//            if(jwtService.isTokenValid(refreshToken,user)){
//                var accessToken= jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user,accessToken);
//                var authResponse=AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
//            }
//        }
//    }
public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response) throws IOException {
    final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String nicNo;
    if(authHeader==null||!authHeader.startsWith("Bearer ")){
        return;
    }
    refreshToken=authHeader.substring(7);
    nicNo=jwtService.extractUsername(refreshToken);
    if(nicNo!=null){
        var user=this.userRepository.findByNicNo(nicNo).orElseThrow();
        if(jwtService.isTokenValid(refreshToken,user)){
            var accessToken= jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user,accessToken);
            var authResponse=AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .role(user.getRole())
                    .build();
            new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
        }
    }
}
}
